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
  selector: 'app-editor-revision',
  templateUrl: './editor-revision.component.html',
  styleUrls: ['./editor-revision.component.css']
})
export class EditorRevisionComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService) { }
  id:any;
  paper:Paper;
  reviews:ReviewDTO[];
  start:any;
  marked = false;
  theCheckbox = false;
  poruka:any;
  answer:Answer=new Answer();
  public simpleQuery = new SimpleQuery;

  options: string[];
  no: Suggeation;
  naucnaOblast: typeof Suggeation = Suggeation;
  review: ReviewDTO = new ReviewDTO();
  commentAuthor: any = '';
  suggestion: string = '';
  commentEditor: string = '';
  newReviewers: string = '';
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
    })
    this.getPaperPDF();
    this.getReviewsForEditor();
    this.start=false;
    const x = Suggeation;
    const options = Object.keys(Suggeation);
    this.options = options.slice(options.length / 2);
  }

  getPaperPDF(){
    this.publicationService.getPaperPDFWithTask(this.id).subscribe(response => {
      this.paper = response;
      console.log(this.paper);
      //this.start=true;
    });
  }

  getReviewsForEditor(){
    this.publicationService.getReviewsForEditor(this.id).subscribe(response => {
      this.reviews = response;
      console.log(this.reviews);
      this.start=true;
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
    this.review.suggestion = this.suggestion;
    if(this.marked===true){
      this.review.newReviewers = "yes";
    }
    else{
      this.review.newReviewers = "no";
    }
    this.publicationService.postEditorReview(this.id, this.review)
      .subscribe(
        data => {
          console.log(data);
          this.notificationService.info("Uspesno ste dodali reviziju kao editor!");
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
