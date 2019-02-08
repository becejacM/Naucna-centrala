import { Component, OnInit } from '@angular/core';
import { SearchService } from '../../services/search/search.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';

@Component({
  selector: 'app-fail-subscription',
  templateUrl: './fail-subscription.component.html',
  styleUrls: ['./fail-subscription.component.css']
})
export class FailSubscriptionComponent implements OnInit {
  id:any;

  constructor(private searchService: SearchService,private notificationService: NotificationsService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.fail();
  }
fail() {
    this.searchService.failSubscription(this.id)
      .subscribe(
        data => {
          console.log(data);
          this.notificationService.info("Doslo je do greske prilikom Vase pretplate!");
        },
        error => {
          console.log(error);
        }
      );
  }
}
