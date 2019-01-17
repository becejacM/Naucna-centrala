import {Component, OnInit} from '@angular/core';

import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';

import {NgxPermissionsService} from 'ngx-permissions';
import {ToastrService} from 'ngx-toastr';

import {AuthenticationService} from '../../services/authentication/authentication.service';
import {LoggedUtils} from '../../utils/logged-utils';
import { NotificationsService } from 'angular2-notifications';


@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css'],
  providers: [AuthenticationService]
})
export class AuthenticationComponent implements OnInit {

  loginForm;
  private username: string;
  private password: string;

  constructor(private autheticationService: AuthenticationService, private permissionsService: NgxPermissionsService,
    private router: Router, private toastr: ToastrService,
     private route: ActivatedRoute, private notificationService: NotificationsService) {
    this.username = '';
    this.password = '';
  }

  ngOnInit() {
    //this.notificationService.success('You are logged in');
        
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)])),
      password: new FormControl('', Validators.compose([Validators.required, Validators.minLength(3)]))
    });
  }

  authenticate(loginForm) {
    this.autheticationService.authenticateUser(Object.values(loginForm)[0], Object.values(loginForm)[1]).subscribe(
      data => {
        localStorage.setItem('loggedUser', JSON.stringify(data)),
          this.router.navigate(['/home']);

        const perm = [];
        perm.push(LoggedUtils.getRole());
        this.permissionsService.loadPermissions(perm);
        this.permissionsService.permissions$.subscribe((item) => {
        });
        console.log("evo me");
        this.notificationService.success('You are logged in');
        this.toastr.success('You are logged in', 'Welcome!');
      },
      error => this.toastr.error('Incorrect username and/or password'),
      () => console.log(JSON.parse(localStorage.getItem('loggedUser')))
    );
  }

}
