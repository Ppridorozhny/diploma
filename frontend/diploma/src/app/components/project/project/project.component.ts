import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../../service/alert.service";
import {first} from "rxjs/operators";
import {Project} from "../../../model/project";
import {ProjectService} from "../../../service/project.service";

@Component({
  selector: 'app-ticket',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  state: string = "project";
  project: Project;
  projectId: number;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private spinner: NgxSpinnerService,
              private alertService: AlertService,
              private projectService: ProjectService
  ) {
  }

  ngOnInit() {
    this.spinner.show();

    this.route.params.subscribe(params => {
      this.projectId = params['projectId'];
    }, () => {
      this.alertService.error('Unsuccessful parameters loading', 'Loading error');
    });

    this.getProject();

    this.spinner.hide();
  }

  getProject() {
    this.projectService.getById(this.projectId).pipe(first())
      .subscribe(project => {
          this.project = project;
        },
        () => this.alertService.error('Cannot load the project'));
  }

  editProject() {
    this.router.navigate(["/project/" + this.projectId + "/edit"]);
  }

  createTicket() {
    this.router.navigate(["/project/" + this.projectId + "/ticket/create"]);
  }

}
