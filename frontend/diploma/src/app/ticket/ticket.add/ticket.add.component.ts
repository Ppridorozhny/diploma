import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../service/alert.service";
import {Priority, Resolution, Ticket, TicketType} from "../../model/Ticket";
import {UserService} from "../../service/user.service";
import {User} from "../../model/user";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-ticket.add',
  templateUrl: './ticket.add.component.html',
  styleUrls: ['./ticket.add.component.css']
})
export class TicketAddComponent implements OnInit {

  private ticket: Ticket;
  private projectId: number;
  private reporter: User;
  private users = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private spinner: NgxSpinnerService,
              private alertService: AlertService,
              private userService: UserService
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

    this.userService.getById(this.ticket.reporterId)
      .pipe(first())
      .subscribe(reporter => this.reporter = reporter);

    if (!this.reporter) {
      this.ticket.reporterId = null;
    }

    this.spinner.hide();

    this.userService.getAll()
      .pipe(first())
      .subscribe(users => this.users = users);
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
    this.ticket.labels = [];
  }

}
