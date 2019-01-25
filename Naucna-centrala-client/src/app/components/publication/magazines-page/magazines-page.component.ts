import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { RegistrationService } from '../../../services/registration/registration.service';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import { MagazineDetails } from '../../../model/MagazineDetails';
import { LoggedUtils } from '../../../utils/logged-utils';


@Component({
  selector: 'app-magazines-page',
  templateUrl: './magazines-page.component.html',
  styleUrls: ['./magazines-page.component.css']
})
export class MagazinesPageComponent implements OnInit {

  
  private magazines: any[];
  private processInstanceId: string; 
  private start: boolean;
  private magazine : MagazineDetails;
  //geocoder = new google.maps.Geocoder();
  private stompClient;
  ws: any;
  disabled: boolean;
  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService, private toastr: ToastrService, ) { }

  ngOnInit() {
    this.start = false;
    this.magazine = new MagazineDetails();
  }

  startProcess(){
    this.publicationService.startProcess().subscribe(response => {
      this.magazines = response.magazines;
      this.processInstanceId = response.processInstanceId;
      console.log(this.processInstanceId);
      this.magazines.forEach(element => {
        console.log(element.name);
        this.start = true;
      });
    });
  }

  chooseMagazine(name: string) {
    console.log("Evo me "+ name);
    this.magazine.processInstanceId = this.processInstanceId;
    this.magazine.name = name;
    this.magazine.starter = LoggedUtils.getUsername();
    console.log("starter: " + this.magazine.starter);
    this.publicationService.chooseMagazine(this.magazine).subscribe(response => {
      //this.registrationService.getNextTask(registration.username).subscribe(response => {
        console.log(response);
        //this.ngOnInit();
        if(response!==null){
          console.log(response.taskId + "eve meeeeeeeeeee");          
          //this.router.navigate([`/tasks`]);          
        }
        else{
          this.router.navigate([`/tasks`]);
        }
        
      //});
    },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.router.navigate([`/registration`]);
          this.notificationService.error(err.error.message + '\nError Status ' + err.status);          
          console.log(err.error.message + '\nError Status ' + err.status);
        } else {
          this.router.navigate([`/registration`]);
          this.notificationService.error(err.error.message + '\nError Status ' + err.status);                    
          console.log(err.error.message + '\nError Status ' + err.status);

        }
      }
    )
  }
}
