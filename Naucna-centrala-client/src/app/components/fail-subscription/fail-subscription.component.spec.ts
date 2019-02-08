import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FailSubscriptionComponent } from './fail-subscription.component';

describe('FailSubscriptionComponent', () => {
  let component: FailSubscriptionComponent;
  let fixture: ComponentFixture<FailSubscriptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FailSubscriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FailSubscriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
