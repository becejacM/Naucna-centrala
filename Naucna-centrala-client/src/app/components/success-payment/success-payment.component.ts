import { Component, OnInit } from '@angular/core';
import { SearchService } from '../../services/search/search.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';

@Component({
  selector: 'app-success-payment',
  templateUrl: './success-payment.component.html',
  styleUrls: ['./success-payment.component.css']
})
export class SuccessPaymentComponent implements OnInit {

  id:any;
  constructor(private searchService: SearchService,private notificationService: NotificationsService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.success();
  }

  success() {
    this.searchService.success(this.id)
      .subscribe(
        data => {
          this.notificationService.info("Vasa kupovina je uspesno zavrsena!");
        },
        error => {
          console.log(error);
        }
      );
  }
}
