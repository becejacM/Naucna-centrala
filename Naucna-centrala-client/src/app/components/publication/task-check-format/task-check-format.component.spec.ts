import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskCheckFormatComponent } from './task-check-format.component';

describe('TaskCheckFormatComponent', () => {
  let component: TaskCheckFormatComponent;
  let fixture: ComponentFixture<TaskCheckFormatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskCheckFormatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskCheckFormatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
