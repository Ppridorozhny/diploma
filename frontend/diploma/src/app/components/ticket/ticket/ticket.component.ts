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
import {Status} from "../../../model/status";
import {ChangeStatus} from "../../../model/change.status";
import {TicketRelation} from "../../../model/ticket.relation";
import {TicketRelationsService} from "../../../service/ticket.relations.service";
import {RelationType} from "../../../model/relation.type";
import {TicketType} from "../../../model/ticket.type";

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
  availableStatuses: Status[];
  changeButtonDisabled: boolean = true;
  changeStatusModel: ChangeStatus;
  relations: TicketRelation[];
  newRelation: TicketRelation = new TicketRelation();
  relationTypes: any = RelationType;
  keyword = 'name';
  tickets: Ticket[];
  types: TicketType[];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private spinner: NgxSpinnerService,
              private alertService: AlertService,
              private userService: UserService,
              private ticketService: TicketService,
              private commentService: CommentService,
              private relationService: TicketRelationsService
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
    this.getComments();
    this.getRelations();
    this.getTickets();

    this.spinner.hide();
  }

  getPossibleChild(type: TicketType) {
    this.ticketService.getAvailableTypes(type)
      .pipe(first())
      .subscribe(types => this.types = types,
        e => this.alertService.error(e)
      )
    ;
  }

  getTickets() {
    this.ticketService.getAllByProjectId(this.projectId)
      .pipe(first())
      .subscribe(tickets => {
        this.tickets = tickets.filter(t => t.id != this.ticketId);
      }, e => this.alertService.error(e));
  }

  getRelations() {
    this.relationService.getRelationsByTicketId(this.ticketId)
      .pipe(first())
      .subscribe(relations => this.relations = relations
        , e => this.alertService.error(e));
  }

  getComments() {
    this.commentService.getAllByTicketId(this.ticketId)
      .pipe(first())
      .subscribe(comments => {
        this.comments = comments;
        this.commentControl.reset('');
      }, () => {
        this.alertService.error("Cannot get comments")
      });
  }

  getAvailableStatuses(currentStatus: Status) {
    this.ticketService.getAvailableStatuses(currentStatus)
      .pipe(first())
      .subscribe(statuses => {
        this.availableStatuses = statuses;
        this.changeButtonDisabled = false;
        this.changeStatusModel = new ChangeStatus(this.ticketId);
      }, error => this.alertService.error(error));
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
          this.getAvailableStatuses(ticket.status);
          this.getPossibleChild(ticket.type);
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

  doChangeStatus() {
    this.changeButtonDisabled = true;
    this.ticketService.changeStatus(this.changeStatusModel)
      .pipe(first())
      .subscribe(ticket => {
        this.ticket = ticket;
        if (this.changeStatusModel.comment && this.changeStatusModel.comment !== '') {

          this.getComments();
        }
        this.changeStatusModel = new ChangeStatus(ticket.id);
        this.changeButtonDisabled = false;
      }, error => this.alertService.error(error));
  }

  editTicket() {
    this.router.navigate(["/project/" + this.projectId + "/ticket/" + this.ticketId + "/edit"]);
  }

  deleteTicket() {
    this.ticketService.delete(this.ticketId)
      .pipe(first())
      .subscribe(
        () => {
          this.router.navigate(["/project/" + this.projectId + "/ticket"]);
          this.alertService.success("Ticket was deleted successfully");
        }, e => this.alertService.error(e)
      )
  }

  deleteRelation(id: number) {
    this.relationService.delete(id)
      .pipe(first())
      .subscribe(() => {
          this.alertService.success('Relation was deleted successfully');
          this.getRelations();
        },
        e => this.alertService.error(e));
  }

  selectTarget(target: Ticket) {
    this.newRelation.target.id = target.id;
  }

  addNewRelation() {
    this.newRelation.source.id = this.ticketId;
    this.relationService.create(this.newRelation).pipe(first())
      .subscribe(() => {
        this.alertService.success('Relation was added successfully');
        this.newRelation = new TicketRelation();
        this.getRelations();
      }, e => this.alertService.error(e));
  }

  createChild() {
    this.router.navigate(["/project/" + this.projectId + "/ticket/create"],
      {queryParams: {'type': this.ticket.type.toString(), 'parent': this.ticketId.toString()}});
  }

}
