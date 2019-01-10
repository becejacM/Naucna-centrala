import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';


export interface FormModel {
  captcha?: string;
}

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})

export class ChangePasswordComponent implements OnInit {
  public new_pass: string;
  public repeat_pass: string;
  public curr_pass: string;
  public formModel: FormModel = {};

  constructor(private authenticationService: AuthenticationService, private toastr: ToastrService, private router: Router) {
    this.new_pass = '';
    this.repeat_pass = '';
    this.curr_pass = '';
  }

  ngOnInit() {
  }

  changePassword() {
    const changePasswordObject = {
      'currentPassword': this.curr_pass,
      'newPassword': this.new_pass,
      'username': JSON.parse(localStorage.getItem('loggedUser')).username
    };
    this.authenticationService.changePassword(changePasswordObject).subscribe(msg => {
      this.router.navigate(['/home']);
      this.toastr.success(msg.toString());
    }, err => {
      console.log(err);
      this.toastr.error(err.error.toString());
    });
  }
}
