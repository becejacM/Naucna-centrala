import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorRevisionComponent } from './author-revision.component';

describe('AuthorRevisionComponent', () => {
  let component: AuthorRevisionComponent;
  let fixture: ComponentFixture<AuthorRevisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorRevisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorRevisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
