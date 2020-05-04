import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForeCloseComponent } from './fore-close.component';

describe('ForeCloseComponent', () => {
  let component: ForeCloseComponent;
  let fixture: ComponentFixture<ForeCloseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForeCloseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForeCloseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
