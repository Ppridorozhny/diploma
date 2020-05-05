import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../../service/alert.service";
import {first} from "rxjs/operators";
import {Project} from "../../../model/project";
import {ProjectService} from "../../../service/project.service";

@Component({
  selector: 'app-project.add',
  templateUrl: './project.add.component.html',
  styleUrls: ['./project.add.component.css']
})
export class ProjectAddComponent implements OnInit {

  project: Project;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private spinner: NgxSpinnerService,
              private projectService: ProjectService,
              private alertService: AlertService
  ) {
  }

  ngOnInit() {
    this.spinner.show();

    console.log('project')

    this.project = new Project();
    this.resetProject();

    this.spinner.hide();
  }

  resetProject() {
    this.project.name = "";
    this.project.description = "";
  }

  addProject() {
    this.spinner.show();
    this.projectService.create(this.project).pipe(first())
      .subscribe(project => {
        this.alertService.success("Project was created");
        this.spinner.hide();
        this.redirectToProject(project);

        this.resetProject();
      }, () => {
        this.alertService.error('Unsuccessful ticket adding', 'Adding error');
        this.spinner.hide();
      });
  }

  redirectToProject(project: Project) {
    this.router.navigate(["/project/" + project.id]);
  }
}
