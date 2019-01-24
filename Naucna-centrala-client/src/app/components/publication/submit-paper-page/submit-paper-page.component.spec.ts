import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitPaperPageComponent } from './submit-paper-page.component';

describe('SubmitPaperPageComponent', () => {
  let component: SubmitPaperPageComponent;
  let fixture: ComponentFixture<SubmitPaperPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitPaperPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitPaperPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
