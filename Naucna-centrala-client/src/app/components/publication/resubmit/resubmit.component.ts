import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../../services/publication/publication.service';
import { Paper } from '../../../model/Paper';

@Component({
  selector: 'app-resubmit',
  templateUrl: './resubmit.component.html',
  styleUrls: ['./resubmit.component.css']
})
export class ResubmitComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute,private notificationService: NotificationsService, private publicationService: PublicationService) { }

  id:any;
  rad: any ='';
  paper: Paper = new Paper();

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
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


  submit() {
    console.log(this.rad);
    this.paper.rad = this.radic;

    this.publicationService.resubmit(this.paper, this.id)
      .subscribe(
        data => {
          console.log(data);
          this.notificationService.info("Uspesno ste dodali clanak!");
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
