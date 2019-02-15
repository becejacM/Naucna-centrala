import { Component, OnInit } from '@angular/core';
import { Paper } from '../../../model/Paper';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import { Suggeation } from '../../../model/Suggestion';
import { saveAs } from "file-saver";
import { SimpleQuery } from '../../../model/simpleQuery';
import { ReviewDTO } from '../../../model/ReviewDTO';

@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.css']
})
export class AddReviewComponent implements OnInit {

  id: any;
  paper:Paper;
  start:any;
  options: string[];
  no: Suggeation;
  naucnaOblast: typeof Suggeation = Suggeation;
  public simpleQuery = new SimpleQuery;
  review: ReviewDTO = new ReviewDTO();

  commentAuthor: any = '';
  suggestion: string = '';
  commentEditor: string = '';
  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService) { }

 
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
    })
    this.getPaperPDF();
    this.start=false;
    const x = Suggeation;
    const options = Object.keys(Suggeation);
    this.options = options.slice(options.length / 2);

  }

  getPaperPDF(){
    this.publicationService.getPaperPDFWithTask(this.id).subscribe(response => {
      this.paper = response;
      console.log(this.paper);
      this.start=true;
      //this.router.navigate([`/tasks`]);

    });
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
    this.review.commentAuthor = this.commentAuthor;
    this.review.suggestion = this.suggestion;
    this.review.commentEditor = this.commentEditor;

    this.publicationService.postReview(this.id, this.review)
      .subscribe(
        data => {
          console.log(data);
          this.notificationService.info("Uspesno ste dodali reviziju!");
          this.router.navigate([`/home`]);

        },
        error => {
          console.log(error);
          this.notificationService.info("Doslo je do greske prilikom dodavanja clanka!");
          this.router.navigate([`/home`]);

        }
      );

  }
}
