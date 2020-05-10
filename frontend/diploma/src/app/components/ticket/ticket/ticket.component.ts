import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../../service/alert.service";
import {UserService} from "../../../service/user.service";
import {TicketService} from "../../../service/ticket.service";
import {Ticket} from "../../../model/ticket";
import {first} from "rxjs/operators";
import {User} from "../../../model/user";
import {Resolution} from "../../../model/resolution";
import {FormControl} from "@angular/forms";
import {Commentt} from "../../../model/commentt";
import {CommentService} from "../../../service/comment.service";

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent implements OnInit {

  state: string = "tickets";
  ticket: Ticket;
  projectId: number;
  ticketId: number;
  reporter: User;
  assignee: User;
  time: string;
  date: string;
  resolutions: any = Resolution;
  epic: Ticket;
  commentControl = new FormControl();
  isSubmitting = false;
  comments: Commentt[];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private spinner: NgxSpinnerService,
              private alertService: AlertService,
              private userService: UserService,
              private ticketService: TicketService,
              private commentService: CommentService
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
    this.commentService.getAllByTicketId(this.ticketId)
      .pipe(first())
      .subscribe(comments => {
        this.comments = comments;
      }, () => {
        this.alertService.error("Cannot get comments")
      })

    this.spinner.hide();
  }

  getTicket() {
    this.ticketService.getTicketById(this.ticketId).pipe(first())
      .subscribe(ticket => {
          this.ticket = ticket;
          let loadDate = this.ticket.dueDate.split(" ");
          this.date = loadDate[0];
          this.time = loadDate[1];
          if (ticket.assigneeId) {
            this.userService.getById(ticket.assigneeId).pipe(first())
              .subscribe(user => this.assignee = user);
          }
          this.userService.getById(ticket.reporterId).pipe(first())
            .subscribe(user => this.reporter = user);
          if (ticket.epicId) {
            this.ticketService.getTicketById(ticket.epicId).pipe(first())
              .subscribe(epic => this.epic = epic)
          }
        },
        () => this.alertService.error('Cannot load the ticket'));
  }

  addComment() {
    this.isSubmitting = true;

    const commentBody = this.commentControl.value;
    let createdComment = new Commentt();
    createdComment.text = commentBody;
    createdComment.ticketId = this.ticket.id;
    this.spinner.show();
    this.commentService
      .create(createdComment)
      .pipe(first())
      .subscribe(
        comment => {
          this.comments.unshift(comment);
          this.commentControl.reset('');
        },
        () => {
          this.alertService.error('Adding comment failed', 'Posting' +
            ' comment error');
        }
      );
    this.spinner.hide();
    this.isSubmitting = false;
  }

  onDeleteComment(commentId: number) {
    this.commentService.delete(commentId)
      .pipe(first())
      .subscribe(
        () => {
          this.comments = this.comments.filter(comment => comment.id !== commentId);
          this.alertService.success("Comment removed successfully")
        }, () => {
          this.alertService.error("Error during comment deleting")
        }
      );
  }

}
