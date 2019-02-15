import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import { Paper } from '../../../model/Paper';
import { ReviewersDTO } from '../../../model/ReviewerDTO';

@Component({
  selector: 'app-choose-new-reviewer',
  templateUrl: './choose-new-reviewer.component.html',
  styleUrls: ['./choose-new-reviewer.component.css']
})
export class ChooseNewReviewerComponent implements OnInit {

  id: any;
  paper:Paper;
  start:any;

  reviewers = new Array<ReviewersDTO>();

  choosenReviewers = new Array<ReviewersDTO>();
  date: string;
  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService) { }

 
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
    })
    this.getPaperInfo();
    this.getReviewers();
    this.start=false;

  }
  getPaperInfo(){
    this.publicationService.getPaperInfoWithTask(this.id).subscribe(response => {
      this.paper = response;
      console.log(this.paper);
      this.start=true;
    });
  }

  getReviewers(){
    this.publicationService.getRevizoriSviTask(this.id).subscribe(response => {
      this.reviewers = response;
      console.log(this.paper);
      this.start=true;
    });
  }

  filter(){
    this.publicationService.getRevizoriNOTask(this.id).subscribe(response => {
      this.reviewers = response;
      console.log(this.paper);
      this.start=true;
    });
  }

  
  addReviewer(reviewer: ReviewersDTO) {
    if (!this.choosenReviewers.some(r => r.username === reviewer.username) && this.choosenReviewers.length === 0) {
      this.choosenReviewers.push(reviewer);
    }
  }

  removeReviewer(reviewer: ReviewersDTO) {
    const index: number = this.choosenReviewers.indexOf(reviewer);
    if (index !== -1) {
      this.choosenReviewers.splice(index, 1);
    }
  }

  submitReviewers() {
    this.publicationService.addNewReviewer(this.id, this.choosenReviewers[0], `PT${this.date}S`).subscribe(res => {
      this.notificationService.info("Uspesno ste dodali revizora!");
      this.router.navigate([`/home`]);
    });
  }
}
