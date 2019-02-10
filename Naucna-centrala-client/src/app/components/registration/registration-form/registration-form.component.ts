import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { RegistrationDetailsDTO } from '../../../model/RegistrationDetailsDTO';
import { FormField } from '../../../model/FormField';

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class RegistrationFormComponent implements OnInit {

  @Input()
  formFields: any[];

  @Output()
  registrationExecuted: EventEmitter<FormField[]> = new EventEmitter();

  registrationDetails: RegistrationDetailsDTO;

  constructor() {
    this.registrationDetails = new RegistrationDetailsDTO();
  }

  ngOnInit() {
  }

  signup() {
    
    this.registrationExecuted.emit(this.formFields);
  }
}
