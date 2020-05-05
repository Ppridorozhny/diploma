import {Component, OnInit} from '@angular/core';
import {Ticket} from "../../../model/ticket";
import {User} from "../../../model/user";
import {Priority} from "../../../model/priority";
import {TicketType} from "../../../model/ticket.type";
import {ActivatedRoute, Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../../service/alert.service";
import {UserService} from "../../../service/user.service";
import {TicketService} from "../../../service/ticket.service";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-ticket.edit',
  templateUrl: './ticket.edit.component.html',
  styleUrls: ['./ticket.edit.component.css']
})
export class TicketEditComponent implements OnInit {

  ticket: Ticket;
  projectId: number;
  ticketId: number;
  reporter: User;
  users: User[] = [];
  currentDate: string;
  priorities: any = Priority;
  time: string;
  date: string;
  keywordAssignee = 'username';
  keyword = 'name';
  epics: Ticket[] = [];
  currentAssignee: string;
  currentEpic: string;
  types: any = TicketType;
  label: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private spinner: NgxSpinnerService,
              private alertService: AlertService,
              private userService: UserService,
              private ticketService: TicketService
  ) {
  }

  ngOnInit() {
    this.spinner.show();

    this.route.params.subscribe(params => {
      this.projectId = params['projectId'];
      this.ticketId = params['ticketId'];
    }, () => {
      this.alertService.error('Unsuccessful parameters loading', 'Loading error');
    });

    this.getTicket();

    this.spinner.hide();

    this.userService.getAll()
      .pipe(first())
      .subscribe(users => {
        this.users = users;
        setTimeout(() => {
            let assignee = this.users
              .find(user => user.id === this.ticket.assigneeId);
            this.currentAssignee = assignee ? assignee.username : "Enter user name";
          }, 2000
        )
      });

    this.ticketService.getAllEpics()
      .pipe(first())
      .subscribe(epics => {
        this.epics = epics;
        setTimeout(() => {
          let epicName = this.epics
            .find(epic => epic.id === this.ticket.epicId);
          this.currentEpic = epicName ? epicName.name : "Enter Epic name";
        }, 2000)
      });
  }

  getTicket() {
    this.ticketService.getTicketById(this.ticketId).pipe(first())
      .subscribe(ticket => {
          this.ticket = ticket;
          let loadDate = this.ticket.dueDate.split(" ");
          this.date = loadDate[0];
          this.time = loadDate[1];
        },
        () => this.alertService.error('Cannot load the ticket'));
  }

  formatDate() {
    this.ticket.dueDate = this.date + " " + this.time;
  }

  updateTicket() {
    this.spinner.show();
    this.formatDate();
    this.ticketService.update(this.ticket).pipe(first())
      .subscribe(ticket => {
        this.alertService.success("Ticket was updated");
        this.spinner.hide();
        this.redirectToTicket(ticket);
      }, () => {
        this.alertService.error('Update failed', 'Update error');
        this.spinner.hide();
      });
  }

  redirectToTicket(ticket: Ticket) {
    this.router.navigate(["/project/" + this.projectId + "/ticket/" + ticket.id]);
  }

  selectAssignee(user: User) {
    this.ticket.assigneeId = user.id;
  }

  selectEpic(epic: Ticket) {
    this.ticket.epicId = epic.id;
  }

  addLabel() {
    if (this.label.length > 2 && this.label.length < 31 && /^[_A-Za-z0-9]*$/.test(this.label)) {

      if (!this.ticket.labels) this.ticket.labels = [];

      this.ticket.labels.push(this.label);

      this.label = '';
    }
  }

  deleteLabel(label: string) {
    const index = this.ticket.labels.indexOf(label);
    if (index !== -1) {
      this.ticket.labels.splice(index, 1)
    }
  }

}
