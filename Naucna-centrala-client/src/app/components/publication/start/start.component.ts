import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { MagazineDetails } from '../../../model/MagazineDetails';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService, private toastr: ToastrService ) { }

  ngOnInit() {
  }

  private magazines: any[];
  private processInstanceId: string; 
  private start: boolean;
  private magazine : MagazineDetails;
  
  startProcess(){
    //novo
    this.publicationService.startProcess().subscribe(response => {
      /*this.magazines = response.magazines;
      this.processInstanceId = response.processInstanceId;
      console.log(this.processInstanceId);
      this.magazines.forEach(element => {
        console.log(element.name);
        this.start = true;
      });*/
      this.router.navigate([`/tasks`]);

    });
  }

}
