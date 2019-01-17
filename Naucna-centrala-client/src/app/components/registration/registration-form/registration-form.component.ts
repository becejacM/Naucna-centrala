import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { RegistrationDetailsDTO } from '../../../model/RegistrationDetailsDTO';

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class RegistrationFormComponent implements OnInit {

  @Input()
  formFields: any[];

  @Output()
  registrationExecuted: EventEmitter<RegistrationDetailsDTO> = new EventEmitter();

  registrationDetails: RegistrationDetailsDTO;

  constructor() {
    this.registrationDetails = new RegistrationDetailsDTO();
  }

  ngOnInit() {
  }

  register() {
    this.registrationExecuted.emit(this.registrationDetails);
  }

}
