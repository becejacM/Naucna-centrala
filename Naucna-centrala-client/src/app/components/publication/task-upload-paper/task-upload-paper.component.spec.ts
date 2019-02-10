import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskUploadPaperComponent } from './task-upload-paper.component';

describe('TaskUploadPaperComponent', () => {
  let component: TaskUploadPaperComponent;
  let fixture: ComponentFixture<TaskUploadPaperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskUploadPaperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskUploadPaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
