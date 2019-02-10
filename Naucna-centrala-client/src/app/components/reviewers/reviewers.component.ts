import { Component, OnInit } from '@angular/core';
import { NotificationsService } from 'angular2-notifications';
import { PublicationService } from '../../services/publication/publication.service';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { naucnaOblast } from '../../model/naucnaOblast';

@Component({
  selector: 'app-reviewers',
  templateUrl: './reviewers.component.html',
  styleUrls: ['./reviewers.component.css']
})
export class ReviewersComponent implements OnInit {

  options: string[];
  no: naucnaOblast;
  naucnaOblast: typeof naucnaOblast = naucnaOblast;

  public pojam;
  public revizori=[];
  constructor(private route: ActivatedRoute,private router: Router, private taskService: PublicationService, private notificationService: NotificationsService) { }

  ngOnInit() {
    const x = naucnaOblast;
    const options = Object.keys(naucnaOblast);
    this.options = options.slice(options.length / 2);
    this.nadjiRevizore();
  }

  nadjiRevizore(){
    this.taskService.getRevizoriSvi().subscribe(response => {
      this.revizori = response;
      console.log(this.revizori);
    });
  }

  search(){
    console.log(this.pojam);
    this.taskService.getRevizori(this.pojam).subscribe(response => {
      this.revizori = response;
      console.log(this.revizori);
    });
  }
}
