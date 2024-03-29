import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { NgxPermissionsService } from 'ngx-permissions';
import { ToastrService } from 'ngx-toastr';

import { LoggedUtils } from '../../utils/logged-utils';
import { NotificationsService } from 'angular2-notifications';

import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

  private perm;

  constructor(private router: Router, private toastr: ToastrService,
    private permissionsService: NgxPermissionsService, private notificationService: NotificationsService) {
  }

  ngOnInit() {
    console.log(LoggedUtils.getRoles());
    /*let str = LoggedUtils.getRoles();
    str = str.replace('[','');
    str = str.replace(']','');
    var regex = new RegExp(' ', 'g')
    str = str.replace(regex,'');    
    console.log(String);     
    var splitted = str.split(","); 
    console.log(splitted); 
    splitted.forEach(element => {
      console.log(element);
      //perm.push(element);      
    }); */

    const perm = [];

    perm.push(LoggedUtils.getRole());
    this.permissionsService.loadPermissions(perm);
    this.permissionsService.permissions$.subscribe((permisios) => {
    });
    //if(LoggedUtils.getUser()!==null){
    /*}
    else{
      if (this.ws != null) {
        console.log("disconnect");
        this.ws.ws.close();
      }
    }*/

    this.initializeWebSocketConnection();

  }

  private stompClient;
  ws: any;
  disabled: boolean;


  initializeWebSocketConnection() {
    if (this.ws != null) {
      console.log("disconnect");
      this.ws.ws.close();
    }
    //let ws = new SockJS(http://localhost:8080/greeting);
    //var socket = new SockJS('wss://localhost:8443/socket');
    //this.ws = Stomp.over(socket);
    console.log("connect");
    console.log(Stomp);
    let socket = new WebSocket("ws://localhost:8088/nc");
    this.ws = Stomp.over(socket);
    let that = this;
    //this.sendName();
    this.ws.connect({}, function (frame) {

      that.ws.subscribe("/nc/notifyTest", function (message) {
        console.log(message);
        that.notificationService.info("Testni soket radi!");        
      });
      that.ws.subscribe("/nc/errors", function (message) {
        console.log(message);
        that.notificationService.error("Greska prilikom registracije! Pokusajte ponovo!");        
      });

      that.ws.subscribe("/nc/notifyAboutOpenAccess", function (message) {
        console.log(message);
        that.notificationService.info("Casopis nije open-access! Autorima se ne naplacuje");        
      });
      that.ws.subscribe("/nc/notifyAboutActiveFee", function (message) {
        console.log(message);
        that.notificationService.info("Vasa pretplata na open-access casopis je aktivna!");        
      });
      that.ws.subscribe("/nc/notifyAboutInvalidPaper", function (message) {
        console.log(message);
        that.notificationService.info("Vasa unos rada je neuspesan! Neki od parametara nisu validni");        
      });
      that.ws.subscribe("/nc/notifyAboutEndOfProcess", function (message) {
        console.log(message);
        that.notificationService.info("Isteklo je vreme u trajanju od 45 dana za objavu Vaseg rada");        
      });
      that.ws.subscribe("/nc/notifyAboutSuccessSubscribe", function (message) {
        console.log(message);
        that.notificationService.info("Vasa pretplata je uspesno zavrsena");    
        that.router.navigate([`/home`]);    
      });
      that.ws.subscribe("/nc/notifyAboutUnSuccessSubscribe", function (message) {
        console.log(message);
        that.notificationService.info("Doslo je do greske prilikom Vase pretplate na casopis");        
      });
      
      
    }, function (error) {
      console.log("STOMP error " + error);
    });
  }
  logoutf() {
    /*if (this.ws != null) {
      console.log("disconnect");
      this.ws.ws.close();
    }*/
    LoggedUtils.clearLocalStorage();
    this.router.navigate(['/login']);
    this.notificationService.success('Uspesno ste se izlogovali!');    
    //this.toastr.success('You are loged out!');
    this.permissionsService.flushPermissions();
  }


  getId(): number {
    return LoggedUtils.getId();
  }


}
