import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorRevisionComponent } from './editor-revision.component';

describe('EditorRevisionComponent', () => {
  let component: EditorRevisionComponent;
  let fixture: ComponentFixture<EditorRevisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorRevisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorRevisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
