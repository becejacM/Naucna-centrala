import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckTematicComponent } from './check-tematic.component';

describe('CheckTematicComponent', () => {
  let component: CheckTematicComponent;
  let fixture: ComponentFixture<CheckTematicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckTematicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckTematicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
