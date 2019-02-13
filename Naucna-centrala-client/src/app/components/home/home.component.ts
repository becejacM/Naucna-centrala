import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import {LoggedUtils} from '../../utils/logged-utils';
import { MagazineDetails } from '../../model/MagazineDetails';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { }

  private user;
  
  ngOnInit() {
    this.user = LoggedUtils.getRole();
    this.router.navigate([`/tasks`]);

  }

}
