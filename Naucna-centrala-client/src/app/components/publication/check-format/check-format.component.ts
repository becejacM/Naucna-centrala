import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import { Paper } from '../../../model/Paper';
import { Answer } from '../../../model/answer';
import { saveAs } from "file-saver";
import { SimpleQuery } from '../../../model/simpleQuery';

@Component({
  selector: 'app-check-format',
  templateUrl: './check-format.component.html',
  styleUrls: ['./check-format.component.css']
})
export class CheckFormatComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService) { }
  id:any;
  paper:Paper;
  start:any;
  marked = false;
  theCheckbox = false;
  poruka:any;
  answer:Answer=new Answer();
  date: string;

  public simpleQuery = new SimpleQuery;

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
    })
    this.getPaperPDF();
    this.start=false;
  }

  getPaperPDF(){
    this.publicationService.getPaperPDFWithTask(this.id).subscribe(response => {
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
      this.answer.answer=this.poruka;
    }
    this.publicationService.answerFormat(this.answer, this.id, `${this.date}`)
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
}

