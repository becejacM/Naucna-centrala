import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationService } from '../../../services/publication/publication.service';

@Component({
  selector: 'app-tasks-page',
  templateUrl: './tasks-page.component.html',
  styleUrls: ['./tasks-page.component.css']
})
export class TasksPageComponent implements OnInit {
  tasks: any[];
  
    constructor(private route: ActivatedRoute, private taskService: PublicationService) { }
  
    ngOnInit() {
      console.log("getujem taskove");
      this.taskService.getTasks().subscribe((response) => {
        console.log(response);
        this.tasks = response;
      });
    }

}
