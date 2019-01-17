import { Component, OnInit } from '@angular/core';
import { RegistrationDetailsDTO } from '../../../model/RegistrationDetailsDTO';
import { ActivatedRoute, Router } from '@angular/router';
import { RegistrationService } from '../../../services/registration/registration.service';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {


  private formFields: any[];
  private processInstanceId: string;
  private task: any;
  private start: boolean;
  //geocoder = new google.maps.Geocoder();
  private stompClient;
  ws: any;
  disabled: boolean;
  constructor(private router: Router, private route: ActivatedRoute, private registrationService: RegistrationService, private toastr: ToastrService, ) { }

  ngOnInit() {
    this.start = false;

  }

  startRegisterProcess() {
    this.registrationService.startProcess().subscribe(response => {
      this.formFields = response.formFields;
      this.processInstanceId = response.processInstanceId;
      console.log(this.processInstanceId);
      this.formFields.forEach(element => {
        console.log(element.id);
        this.start = true;
      });
    });
  }
  register(registration: RegistrationDetailsDTO) {
    console.log("Evo me");
    console.log(registration);
    registration.processInstanceId = this.processInstanceId;
    console.log("processsssssssss: " + registration.processInstanceId);
    //this.tempDataService.tempData = registration;
    //this.setLatLng(registration).then((updatedReg) => {
    this.registrationService.register(registration).subscribe(response => {
      this.registrationService.getNextTask(registration.username).subscribe(response => {
        console.log(response);
        //this.ngOnInit();
        if(response!==null){
          console.log(response.taskId + "eve meeeeeeeeeee");          
          this.router.navigate([`/registration/error/${response.taskId}`]);          
        }
        else{
          this.toastr.success("Your registration is successfully created. Please check your email for confirmation!");
          this.router.navigate([`/login`]);
        }
        


        //});
      });
    },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.router.navigate([`/registration/error`]);
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
          console.log(err.error.message + '\nError Status ' + err.status);
        } else {
          this.router.navigate([`/registration/error`]);
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
          console.log(err.error.message + '\nError Status ' + err.status);

        }
      }
    )
  }
  
  setLatLng(registration: RegistrationDetailsDTO): Promise<any> {
    const promise = new Promise((resolve, reject) => {
      //const address = `${registration.city}, ${registration.place}, Serbia, ${registration.zipCode}`;
      //this.geocoder.geocode({address: address}, (results, status) => {
      //if (status === google.maps.GeocoderStatus.OK) {
      //const latlong = results[0].geometry.location;
      //registration.lat = `${latlong.lat()}`;
      //registration.lng = `${latlong.lng()}`;
      //registration.lat = '0.2';
      //registration.lng = '2.2';
      resolve(registration);
      //} else {
      //reject("something went wrong");
      //}
      //});
    });
    return promise;
  }

}
