import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model/user";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  @Input() states: string;
  @Input() projectId: number;
  profile: User;
  friendRequests: number;

  constructor() {
  }

  ngOnInit() {
    this.profile = JSON.parse(localStorage.getItem('currentUser'));
  }

}
