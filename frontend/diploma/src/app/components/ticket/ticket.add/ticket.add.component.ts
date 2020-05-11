import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../../service/alert.service";
import {Ticket} from "../../../model/ticket";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user";
import {first} from "rxjs/operators";
import {TicketService} from "../../../service/ticket.service";
import {Priority} from "../../../model/priority";
import {TicketType} from "../../../model/ticket.type";
import {Resolution} from "../../../model/resolution";
import {Status} from "../../../model/status";

@Component({
  selector: 'app-ticket.add',
  templateUrl: './ticket.add.component.html',
  styleUrls: ['./ticket.add.component.css']
})
export class TicketAddComponent implements OnInit {

  ticket: Ticket;
  projectId: number;
  reporter: User;
  users: User[] = [];
  currentDate: string;
  priorities: any = Priority;
  types: TicketType[];
  type: TicketType = TicketType.DEFAULT;
  parentId: number;
  time: string;
  date: string;
  keywordAssignee = 'username';
  keyword = 'name';
  epics: Ticket[] = [];
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

    console.log('ticket')

    this.ticket = new Ticket();
    this.route.params.subscribe(params => {
      this.projectId = params['projectId'];
    }, () => {
      this.alertService.error('Unsuccessful parameters loading', 'Loading error');
    });

    this.route.queryParams.subscribe(params => {
      let type = params['type'];
      if (type) {
        this.type = type;
      }
      this.ticketService.getAvailableTypes(this.type)
        .pipe(first())
        .subscribe(types => this.types = types,
          e => this.alertService.error(e));

      let parent = params['parent'];
      if (parent) {
        this.parentId = parent;
      }
    }, () => {
      this.alertService.error('Unsuccessful parameters loading', 'Loading error');
    });

    this.getCurrentDate();
    this.resetTicket();

    this.userService.getById(this.ticket.reporterId)
      .pipe(first())
      .subscribe(reporter => this.reporter = reporter,
        () => this.ticket.reporterId = null);

    this.spinner.hide();

    this.userService.getAll()
      .pipe(first())
      .subscribe(users => this.users = users);

    this.ticketService.getAllEpics()
      .pipe(first())
      .subscribe(epics => this.epics = epics);
  }

  resetTicket() {
    this.ticket.name = "";
    this.ticket.description = "";
    this.ticket.priority = Priority.MAJOR;
    this.ticket.type = TicketType.TASK;
    this.ticket.resolution = Resolution.UNRESOLVED;
    this.ticket.dueDate = "";
    this.ticket.assigneeId = null;
    this.ticket.reporterId = JSON.parse(localStorage.currentUser).id;
    this.ticket.epicId = null;
    this.ticket.status = Status.OPEN;
    this.ticket.labels = [];
    this.ticket.projectId = this.projectId;
  }

  formatDate() {
    this.ticket.dueDate = this.date + " " + this.time;
  }

  getCurrentDate() {
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    this.currentDate = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
  }

  addTicket() {
    this.spinner.show();
    this.formatDate();
    this.ticketService.createTicket(this.ticket, this.parentId).pipe(first())
      .subscribe(ticket => {
        this.alertService.success("Ticket was created");
        this.spinner.hide();
        this.redirectToTicket(ticket);

        this.resetTicket();
      }, () => {
        this.alertService.error('Unsuccessful ticket adding', 'Adding error');
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
