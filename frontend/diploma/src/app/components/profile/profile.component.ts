import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {User} from "../../model/user";
import {AlertService} from "../../service/alert.service";
import {UserService} from "../../service/user.service";
import {first} from "rxjs/operators";
import {Ticket} from "../../model/ticket";
import {TicketService} from "../../service/ticket.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})

export class ProfileComponent implements OnInit {

  profile: User;
  tickets: Ticket[];

  constructor(private userService: UserService,
              private spinner: NgxSpinnerService,
              private route: ActivatedRoute,
              private alertService: AlertService,
              private ticketService: TicketService) {
  }

  ngOnInit() {
    this.spinner.show();

    this.route.params.subscribe(params => {
      let username = params['username'];
      let currentUser = JSON.parse(localStorage.getItem('currentUser'));

      if (username !== currentUser.username) {
        this.userService.getByUsername(username).pipe(first())
          .subscribe(profile => {
              this.profile = profile;
              this.getTickets();
            },
            error => this.alertService.error(error));
      } else {
        this.profile = currentUser;
        this.getTickets();
      }

      this.spinner.hide();
    }, error => {
      this.alertService.error(error, 'Error');
      this.spinner.hide();
    });
  }

  private getTickets() {
    this.ticketService.getAll().pipe(first())
      .subscribe(tickets => {
        this.tickets = tickets.filter(ticket => ticket.reporterId === this.profile.id ||
          ticket.assigneeId === this.profile.id);
      }, error => this.alertService.error(error));
  }
}
