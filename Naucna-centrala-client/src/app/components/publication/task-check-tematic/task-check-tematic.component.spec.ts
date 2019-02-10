import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskCheckTematicComponent } from './task-check-tematic.component';

describe('TaskCheckTematicComponent', () => {
  let component: TaskCheckTematicComponent;
  let fixture: ComponentFixture<TaskCheckTematicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskCheckTematicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskCheckTematicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
