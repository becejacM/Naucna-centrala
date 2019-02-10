import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RegistrationService } from '../../../services/registration/registration.service';
import { ToastrService } from 'ngx-toastr';import { RegistrationDetailsDTO } from '../../../model/RegistrationDetailsDTO';
import { NotificationsService } from 'angular2-notifications';
import { FormField } from '../../../model/FormField';

@Component({
  selector: 'app-registration-error-page',
  templateUrl: './registration-error-page.component.html',
  styleUrls: ['./registration-error-page.component.css']
})
export class RegistrationErrorPageComponent implements OnInit {

  taskId2: number;
  formFields2: any[];
  isDoneLoading = false;
  registrationDetails: RegistrationDetailsDTO;
  formFields = new Array<FormField>();
  taskId: string;
  constructor(private router: Router, private route: ActivatedRoute, private notificationService: NotificationsService, private registrationService: RegistrationService, private toastr: ToastrService, ) { 
      this.registrationDetails = new RegistrationDetailsDTO();    
  }
  
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params["taskId"];
      console.log("uzimam iddddd "+this.taskId);
      
      this.registrationService.getTaskData(this.taskId).subscribe(response => {
        this.formFields = response.formFields;
        this.isDoneLoading = true;
      })
    })
  }

  register() {
    this.registrationService.register(this.taskId, this.formFields).subscribe(response => {
      console.log(response);
      this.registrationService.getNextTask(response.author).subscribe(response => {
        console.log(response);
        //this.ngOnInit();
        if(response!==null){
          console.log(response.id + "eve meeeeeeeeeee");          
          this.router.navigate([`/registration/error/${response.id}`]);          
        }
        else{
          this.notificationService.success('Your registration is successfully created. Please check your email for confirmation!');
          
          //this.toastr.success("Your registration is successfully created. Please check your email for confirmation!");
          this.router.navigate([`/login`]);
        }
        


        //});
      });
    });
  }
}
