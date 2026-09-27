import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovalComponent } from './approval';

describe('Approval', () => {
  let component: ApprovalComponent;
  let fixture: ComponentFixture<ApprovalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApprovalComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ApprovalComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
