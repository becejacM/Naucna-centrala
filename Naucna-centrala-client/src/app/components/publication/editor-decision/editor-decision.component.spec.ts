import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorDecisionComponent } from './editor-decision.component';

describe('EditorDecisionComponent', () => {
  let component: EditorDecisionComponent;
  let fixture: ComponentFixture<EditorDecisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorDecisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorDecisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
