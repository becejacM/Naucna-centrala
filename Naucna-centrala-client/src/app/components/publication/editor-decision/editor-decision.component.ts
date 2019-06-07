import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import { Paper } from '../../../model/Paper';
import { Answer } from '../../../model/answer';
import { saveAs } from "file-saver";
import { SimpleQuery } from '../../../model/simpleQuery';
import { Suggeation } from '../../../model/Suggestion';
import { ReviewDTO } from '../../../model/ReviewDTO';

@Component({
  selector: 'app-editor-decision',
  templateUrl: './editor-decision.component.html',
  styleUrls: ['./editor-decision.component.css']
})
export class EditorDecisionComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService) { }
  id:any;
  paper:Paper;
  start:any;
  marked = false;
  theCheckbox = false;
  poruka:any;
  answer:Answer=new Answer();
  answer2:Answer=new Answer();

  public simpleQuery = new SimpleQuery;

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
    })
    this.getPaperPDF();
    this.getCommentFromAuthor();
    this.start=false;
  }
  getCommentFromAuthor(){
    this.publicationService.getCommentFromAuthor(this.id).subscribe(response => {
      this.poruka = response.answer;
      console.log(this.paper);
      //this.start=true;
    });
  }
  getPaperPDF(){
    this.publicationService.getPaperPDFWithTask(this.id).subscribe(response => {
      this.paper = response;
      console.log(this.paper);
      //this.start=true;
    });
  }
  toggleVisibility(e){
    this.marked= e.target.checked;
  }
  download() {
    this.simpleQuery.value = this.paper.filename;    
    this.publicationService.download(this.simpleQuery)
      .subscribe(
        data => {
          this.downloadFile(data, this.paper.filename);
        },
        error => {
          console.log(error);
        }
      );
  }

  downloadFile(data, fileName) {
    var blob = new Blob([data], { type: 'application/pdf' });
    saveAs(blob, fileName);
  }

  submit() {
    if(this.marked===true){
      this.answer.answer = "yes";
    }
    else{
      this.answer.answer = "no";
    }
    this.publicationService.answerEditorDecision(this.answer, this.id)
      .subscribe(
        data => {
          console.log(data);
          this.notificationService.info("Uspesno ste dodali odluku kao editor!");
          this.router.navigate([`/home`]);

        },
        error => {
          console.log(error);
          this.notificationService.info("Doslo je do greske prilikom dodavanja revizije!");
          this.router.navigate([`/home`]);

        }
      );

  }
}
