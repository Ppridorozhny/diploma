import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {TicketAddComponent} from './ticket.add.component';

describe('Ticket.AddComponent', () => {
  let component: Ticket.AddComponent;
  let fixture: ComponentFixture<Ticket.AddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TicketAddComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
