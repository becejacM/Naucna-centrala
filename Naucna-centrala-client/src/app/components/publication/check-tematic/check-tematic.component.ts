import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import { Paper } from '../../../model/Paper';
import { Answer } from '../../../model/answer';

@Component({
  selector: 'app-check-tematic',
  templateUrl: './check-tematic.component.html',
  styleUrls: ['./check-tematic.component.css']
})
export class CheckTematicComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService) { }
  id:any;
  paper:Paper;
  start:any;
  marked = false;
  theCheckbox = false;
  answer:Answer=new Answer();
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
    })
    this.getPaperInfo();
    this.start=false;
  }

  getPaperInfo(){
    this.publicationService.getPaperInfoWithTask(this.id).subscribe(response => {
      this.paper = response;
      console.log(this.paper);
      this.start=true;
      //this.router.navigate([`/tasks`]);

    });
  }

  toggleVisibility(e){
    this.marked= e.target.checked;
  }
  
  submit() {
    if(this.marked===true){
      this.answer.answer='yes';
    }
    else{
      this.answer.answer="no";
    }
    this.publicationService.answerTematic(this.answer, this.id)
      .subscribe(
        data => {
          console.log(data);
          this.notificationService.info("Uspesno ste dodali odgovor!");
          this.router.navigate([`/home`]);

        },
        error => {
          console.log(error);
          this.notificationService.info("Doslo je do greske prilikom dodavanja odgovora!");
          this.router.navigate([`/home`]);

        }
      );

  }
}
