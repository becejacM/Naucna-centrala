import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  task: any;
  id: string;
  isFetched = false;
  taskDetails: any;

  constructor(private route: ActivatedRoute,private router: Router, private taskService: PublicationService, private notificationService: NotificationsService) { 
    this.taskDetails = {};
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
      this.taskService.getTask(this.id).subscribe(response => {
        this.task = response;
        console.log(response);
        this.formTaskDetails();
        this.isFetched = true;
      })
    })
  }

  completeTask(o?: any) {
    //this.taskDetails = o || this.taskDetails;
    //this.removeNonWriteableFields();
    console.log(this.taskDetails);
    this.taskService.executeTask(this.id, this.taskDetails).subscribe(response => {
      this.notificationService.success(
        response.message
      );
      this.router.navigate(['/home']);
      
    }, error => {
      this.notificationService.error(
        error.error.message
      );
    });
  }

  removeNonWriteableFields() {
    const nonWriteableProps = this.task.props.filter(p => !p.writable);
    nonWriteableProps.forEach(p => delete this.taskDetails[p.id]); 
  }

  formTaskDetails() {
    this.task.formFields.forEach(t => {
      this.taskDetails[t.id] = '';
    });
  }

}
