import {Component, OnInit} from "@angular/core";
import {Project} from "../../model/project";
import {ActivatedRoute} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../service/alert.service";
import {ProjectService} from "../../service/project.service";
import {ChartOptions, ChartType} from "chart.js";
import {Label} from "ng2-charts";
import * as pluginDataLabels from 'chartjs-plugin-datalabels';
import {first} from "rxjs/operators";
import {DashboardService} from "../../service/dashboard.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  state: string = "dashboard";
  project: Project;
  projectId: number;
  lastUpdated: Date = new Date();

  public pieChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      position: 'top',
    },
    plugins: {
      datalabels: {
        formatter: (value, ctx) => {
          const label = ctx.chart.data.labels[ctx.dataIndex];
          return label;
        },
      },
    }
  };
  public pieChartLabels: Label[] = [];
  public pieChartData: number[] = [];
  public pieChartType: ChartType = 'pie';
  public pieChartLegend = true;
  public pieChartPlugins = [pluginDataLabels];
  public pieChartColors = [
    {
      backgroundColor: ['rgba(255,0,0,0.3)', 'rgba(0,255,0,0.3)', 'rgba(0,0,255,0.3)'],
    },
  ];

  constructor(private route: ActivatedRoute,
              private spinner: NgxSpinnerService,
              private alertService: AlertService,
              private projectService: ProjectService,
              private dashboardService: DashboardService,
  ) {
  }

  ngOnInit() {
    this.spinner.show();

    this.route.params.subscribe(params => {
      this.projectId = params['projectId'];
      this.getProject();
      this.getUserStatistics();
    }, () => {
      this.alertService.error('Unsuccessful parameters loading', 'Loading error');
    });

    this.spinner.hide();
  }

  getProject() {
    this.projectService.getById(this.projectId)
      .pipe(first())
      .subscribe(project => this.project = project,
        e => this.alertService.error('Cannot load project'));
  }

  getUserStatistics() {
    this.dashboardService.getUserStatistic(this.projectId)
      .pipe(first())
      .subscribe(statistics => {
        statistics.forEach(statistic => {
          this.pieChartLabels.push(statistic.username);
          this.pieChartData.push(statistic.count);
        });
      }, e => this.alertService.error('Cannot load user`s statistic'));
  }

  updateUserStatistic() {
    this.pieChartLabels = [];
    this.pieChartData = [];
    this.getUserStatistics();
  }

}
