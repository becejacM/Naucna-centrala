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
  private enumValues = [];
  revizori=[];
  naucnaOblast;
  
  constructor(private route: ActivatedRoute,private router: Router, private taskService: PublicationService, private notificationService: NotificationsService) { 
    this.taskDetails = {};
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
      this.taskService.getTask(this.id).subscribe(response => {
        this.task = response;
        console.log(response);
        //this.formTaskDetails();
        this.isFetched = true;
        this.gotToTask();
      })
    })
    
  }

  gotToTask(){
    console.log(this.task.taskDefinitionId);
    if(this.task.taskDefinitionId==='Task_004pb17'){
      let url =this.router.url+'/upload-paper';
      console.log(url);
      this.router.navigate([url]);
    }
    else if(this.task.taskDefinitionId==='Task_095idnu'){
      let url =this.router.url+'/check-tematic';
      console.log(url);
      this.router.navigate([url]);
    }
    else if(this.task.taskDefinitionId==='Task_026kfj5'){
      let url =this.router.url+'/check-format';
      console.log(url);
      this.router.navigate([url]);
    }
    else if(this.task.taskDefinitionId==='Task_0k2mpm2'){
      //odabir casopisa
      let url =this.router.url+'/choose-magazine';
      console.log(url);
      this.router.navigate([url]);
    }
    else if(this.task.taskDefinitionId==='Task_18686dm'){
      //subskripcija casopisa
      let url =this.router.url+'/subscribe';
      console.log(url);
      this.router.navigate([url]);
    }
    else if(this.task.taskDefinitionId==='Task_0f9j2ju'){
      //upload rada casopisa
      let url =this.router.url+'/upload';
      console.log(url);
      this.router.navigate([url]);
    }
    else if(this.task.taskDefinitionId==='Task_0dg9w5t'){
      //provera tematike rada casopisa
      let url =this.router.url+'/checkTematic';
      console.log(url);
      this.router.navigate([url]);
    }
    else if(this.task.taskDefinitionId==='Task_0epjvsx'){
      //provera formata rada casopisa
      let url =this.router.url+'/checkFormat';
      console.log(url);
      this.router.navigate([url]);
    }
    else if(this.task.taskDefinitionId==='Task_1228eof'){
      //korekcija rada
      let url =this.router.url+'/resubmit';
      console.log(url);
      this.router.navigate([url]);
    }
  }
  /*completeTask(o?: any) {
    //this.taskDetails = o || this.taskDetails;
    //this.removeNonWriteableFields();
    console.log(this.taskDetails);
    console.log("ddddddddd*************dddddddd "+this.taskDetails["filename"]);
    
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
        
        if( t.type.name=='enum'){
          console.log(t.type.values);          
          this.enumValues = Object.keys(t.type.values);
        }
    });
  }

  localUrl: any[];
  
  uploadFile(event:any) {
    
    
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      //console.log(file);
      this.taskService.temporaryupload(file)
        .subscribe(data => {
          console.log(data);
          this.taskDetails.filename = data.filename;
          console.log("ddddddddddddddddd "+this.taskDetails["filename"]);
          //this.book.title = data.title.toString();
          //this.book.author = data.author.toString();
          //this.book.filename = data.filename.toString();
          //this.book.keywords = data.keywords.toString();
          //this.book.publicationYear = data.publicationYear.toString();
        });
    }
  }*/
}
