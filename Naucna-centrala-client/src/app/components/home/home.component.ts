import {Component, OnInit} from '@angular/core';

import {LoggedUtils} from '../../utils/logged-utils';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor() { }

  private user;

  ngOnInit() {
    this.user = LoggedUtils.getRole();
  }

}
