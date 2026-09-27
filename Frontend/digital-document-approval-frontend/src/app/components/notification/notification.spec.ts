import { ComponentFixture, TestBed } from '@angular/core/testing';


describe('Notification', () => {
  let component: Notification;
  let fixture: ComponentFixture<Notification>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Notification],
    }).compileComponents();

    fixture = TestBed.createComponent(Notification);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
