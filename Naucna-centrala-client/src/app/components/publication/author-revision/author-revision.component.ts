import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import { Paper } from '../../../model/Paper';
import { ReviewDTO } from '../../../model/ReviewDTO';

@Component({
  selector: 'app-author-revision',
  templateUrl: './author-revision.component.html',
  styleUrls: ['./author-revision.component.css']
})
export class AuthorRevisionComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService) { }

  id:any;
  rad: any ='';
  paper: Paper = new Paper();
  komentarDorada : string = '';
  reviews:ReviewDTO[];
  start:any;
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
      this.getReviewsForAuthor();
    })
  }

  localUrl: any[];
  fileList: FileList;
  radic: any;
  uploadFile(event: any) {
    //ovo koristim

    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      //console.log(file);
      this.publicationService.temporaryupload(file)
        .subscribe(data => {
          console.log(data);
          this.radic = data.filename;
        });
    }
  }

  getReviewsForAuthor(){
    this.publicationService.getReviewsForAuthor(this.id).subscribe(response => {
      this.reviews = response;
      console.log(this.reviews);
      this.start=true;
    });
  }
  submit() {
    console.log(this.rad);
    this.paper.komentarDorada = this.komentarDorada;
    this.paper.rad = this.radic;

    this.publicationService.adapted(this.paper, this.id)
      .subscribe(
        data => {
          console.log(data);
          this.notificationService.info("Uspesno ste dodali preradjen clanak!");
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
