import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Commentt} from "../../../../model/commentt";
import {ActivatedRoute} from "@angular/router";
import {AlertService} from "../../../../service/alert.service";

@Component({
  selector: 'comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.css']
})
export class CommentListComponent implements OnInit {

  @Input() comment: Commentt;
  @Output() deleteComment = new EventEmitter<boolean>();

  username: string;
  ticketId: number;

  constructor(private route: ActivatedRoute,
              private alertService: AlertService
  ) {
  }

  ngOnInit() {
    this.username = JSON.parse(localStorage.getItem('currentUser')).username;
    this.route.params.subscribe(params => {
      this.ticketId = params['ticketId'];
    }, () => {
      this.alertService.error('Unsuccessful parameters loading', 'Loading error');
    });
  }

  deleteClicked() {
    this.deleteComment.emit(true);
  }

}
