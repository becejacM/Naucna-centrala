import { Component, OnInit } from '@angular/core';
import { SearchService } from '../../services/search/search.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';

@Component({
  selector: 'app-success-subscription',
  templateUrl: './success-subscription.component.html',
  styleUrls: ['./success-subscription.component.css']
})
export class SuccessSubscriptionComponent implements OnInit {
  id:any;
  constructor(private searchService: SearchService,private notificationService: NotificationsService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.success();
  }

  success() {
    this.searchService.successSubscription(this.id)
      .subscribe(
        data => {
          this.notificationService.info("Vasa pretplata je uspesno zavrsena!");
        },
        error => {
          console.log(error);
        }
      );
  }

}
