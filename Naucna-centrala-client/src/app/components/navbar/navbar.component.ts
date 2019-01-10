import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {NgxPermissionsService} from 'ngx-permissions';
import {ToastrService} from 'ngx-toastr';

import {LoggedUtils} from '../../utils/logged-utils';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

  private perm;

  constructor(private router: Router, private toastr: ToastrService, 
    private permissionsService: NgxPermissionsService) {
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
  }

  

  logoutf() {
    /*if (this.ws != null) {
      console.log("disconnect");
      this.ws.ws.close();
    }*/
    LoggedUtils.clearLocalStorage();
    this.router.navigate(['/login']);
    this.toastr.success('You are loged out!');
    this.permissionsService.flushPermissions();
  }


  getId(): number {
    return LoggedUtils.getId();
  }


}
