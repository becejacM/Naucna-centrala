import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchService } from '../../services/search/search.service';
import { NotificationsService } from 'angular2-notifications';

@Component({
  selector: 'app-fail-payment',
  templateUrl: './fail-payment.component.html',
  styleUrls: ['./fail-payment.component.css']
})
export class FailPaymentComponent implements OnInit {
  id:any;
  constructor(private searchService: SearchService,private notificationService: NotificationsService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.fail();
  }

  fail() {
    this.searchService.fail(this.id)
      .subscribe(
        data => {
          console.log(data);
          this.notificationService.info("Doslo je do greske prilikom Vase kupovine!");
        },
        error => {
          console.log(error);
        }
      );
  }
}
