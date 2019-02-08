import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessSubscriptionComponent } from './success-subscription.component';

describe('SuccessSubscriptionComponent', () => {
  let component: SuccessSubscriptionComponent;
  let fixture: ComponentFixture<SuccessSubscriptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SuccessSubscriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SuccessSubscriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
