import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../../service/alert.service";
import {first} from "rxjs/operators";
import {Project} from "../../../model/project";
import {ProjectService} from "../../../service/project.service";

@Component({
  selector: 'app-ticket.edit',
  templateUrl: './project.edit.component.html',
  styleUrls: ['./project.edit.component.css']
})
export class ProjectEditComponent implements OnInit {

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
        () => this.alertService.error('Cannot load the ticket'));
  }

  updateProject() {
    this.spinner.show();
    this.projectService.update(this.project).pipe(first())
      .subscribe(project => {
        this.alertService.success("Project was updated successfully");
        this.spinner.hide();
        this.redirectToProject(project);
      }, () => {
        this.alertService.error('Update failed', 'Update error');
        this.spinner.hide();
      });
  }

  redirectToProject(project: Project) {
    this.router.navigate(["/project/" + project.id]);
  }

}
