import { Component, OnInit } from '@angular/core';
import { RegistrationDetailsDTO } from '../../../model/RegistrationDetailsDTO';
import { ActivatedRoute, Router } from '@angular/router';
import { RegistrationService } from '../../../services/registration/registration.service';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { NotificationsService } from 'angular2-notifications';
import { FormField } from '../../../model/FormField';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {


  private formFields2: any[];
  private processInstanceId: string;
  private task: any;
  private start: boolean;
  formFields = new Array<FormField>();
  taskId: string;

  private stompClient;
  ws: any;
  disabled: boolean;
  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private registrationService: RegistrationService, private toastr: ToastrService, ) { }

  ngOnInit() {
    this.start = false;

  }

  startRegisterProcess() {
    this.registrationService.startProcess().subscribe(response => {
      this.formFields = response.formFields;
      this.taskId = response.taskId;
      //this.processInstanceId = response.processInstanceId;
      console.log(this.taskId);
      this.formFields.forEach(element => {
        //console.log(element.id);
        this.start = true;
      });
    });
  }
  register(registration: FormField[]) {
    console.log("Evo me");
    console.log(registration);
    //registration.processInstanceId = this.processInstanceId;
    //console.log("processsssssssss: " + registration.processInstanceId);
    //this.tempDataService.tempData = registration;
    //this.setLatLng(registration).then((updatedReg) => {
    this.registrationService.register(this.taskId, registration).subscribe(response => {
      console.log(response);
      this.registrationService.getNextTask(response.author).subscribe(response => {
        console.log(response);
        //this.ngOnInit();
        if(response!==null){
          console.log(response.id + "eve meeeeeeeeeee");          
          this.router.navigate([`/registration/error/${response.id}`]);          
        }
        else{
          this.notificationService.success('Vasa registracija je uspesno kreirana. Potvrdite Vas nalog putem e-mail-a!');
          this.router.navigate([`/login`]);
        }
        


        //});
      });
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
