import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { RegistrationService } from '../../../services/registration/registration.service';

@Component({
  selector: 'app-verification-page',
  templateUrl: './verification-page.component.html',
  styleUrls: ['./verification-page.component.css']
})
export class VerificationPageComponent implements OnInit {

  id: number;
  processInstanceId : string;
  constructor(private registrationService: RegistrationService, private route: ActivatedRoute) { }

  ngOnInit() {
    console.log("tu saaam");
    this.route.params.subscribe(params => {
      this.id = +params["id"];
      this.processInstanceId = params["processInstanceId"];
      this.registrationService.verify(this.id, this.processInstanceId).subscribe(response => {
        console.log("OK");
      });
    })

  }

}
