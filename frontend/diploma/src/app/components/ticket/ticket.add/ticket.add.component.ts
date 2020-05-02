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
  types: any = TicketType;
  time: string;
  date: string;
  keywordAssignee = 'username';
  keyword = 'name';
  epics: Ticket[] = [];

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

    this.ticket = new Ticket();
    this.route.params.subscribe(params => {
      this.projectId = params['projectId'];
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

    console.log(this.ticket);
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
    this.ticketService.createTicket(this.ticket).pipe(first())
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

}
