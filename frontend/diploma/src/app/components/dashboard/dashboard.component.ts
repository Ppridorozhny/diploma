import {Component, OnInit} from "@angular/core";
import {Project} from "../../model/project";
import {ActivatedRoute, Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../service/alert.service";
import {ProjectService} from "../../service/project.service";
import {ChartDataSets, ChartOptions, ChartType, RadialChartOptions} from "chart.js";
import {Color, Label} from "ng2-charts";
import * as pluginDataLabels from 'chartjs-plugin-datalabels';
import {first} from "rxjs/operators";
import {DashboardService} from "../../service/dashboard.service";
import * as pluginAnnotations from 'chartjs-plugin-annotation';
import {RunInfo} from "../../model/run.info";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  state: string = "dashboard";
  project: Project;
  projectId: number;
  lastUpdatedPie: Date = new Date();
  lastUpdatedRadar: Date = new Date();
  lastUpdatedLinear: Date = new Date();
  lastUpdatedGantt: Date = new Date();
  seriesId: string;
  series: string[];
  runs: RunInfo[];

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
  public radarChartColors = [
    {
      pointHoverBorderColor: ['rgba(179,181,198,1)', 'rgba(0,255,0,0.3)', 'rgba(0,0,255,0.3)'],
    },
  ];

  // Radar
  public radarChartOptions: RadialChartOptions = {
    responsive: true,
    scale: {
      ticks: {
        beginAtZero: true,
      }
    }
  };
  public radarChartLabels: Label[] = [];

  public radarChartData: ChartDataSets[] = [
    {data: [], label: 'Ticket types'}
  ];
  public radarChartType: ChartType = 'radar';

  constructor(private route: ActivatedRoute,
              private router: Router,
              private spinner: NgxSpinnerService,
              private alertService: AlertService,
              private projectService: ProjectService,
              private dashboardService: DashboardService,
  ) {
  }

  public lineChartData: ChartDataSets[] = [
    {data: [], label: 'Opened'},
    {data: [], label: 'Closed'}
  ];
  public lineChartLabels: Label[] = [];
  public lineChartOptions: (ChartOptions & { annotation: any }) = {
    responsive: true,
    scales: {
      // We use this empty structure as a placeholder for dynamic theming.
      xAxes: [{}],
      yAxes: [
        {
          id: 'y-axis-0',
          position: 'left',
        }
      ]
    },
    annotation: {
      annotations: [
        {
          type: 'line',
          mode: 'vertical',
          scaleID: 'x-axis-0',
          value: 'March',
          borderColor: 'orange',
          borderWidth: 2,
          label: {
            enabled: true,
            fontColor: 'orange',
            content: 'LineAnno'
          }
        },
      ],
    },
  };
  public lineChartColors: Color[] = [
    { // red
      backgroundColor: 'rgba(255,0,0,0.3)',
      borderColor: 'red',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    }
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';
  public lineChartPlugins = [pluginAnnotations];

  title = "ng-gstc-test";

  config: any;
  gstcState: any;

  ngOnInit() {
    this.spinner.show();

    this.route.params.subscribe(params => {
      this.projectId = params['projectId'];
      this.getProject();
      this.getUserStatistics();
      this.getTicketTypeStatistics();
      this.getDefectsStatistic();
    }, () => {
      this.alertService.error('Unsuccessful parameters loading', 'Loading error');
    });

    this.route.queryParams.subscribe(params => {
      let ser = params['seriesId']
      if (ser) {
        this.seriesId = ser;
      }
      this.getSeries();
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
          this.pieChartLabels.push(statistic.name);
          this.pieChartData.push(statistic.count);
        });
      }, e => this.alertService.error('Cannot load user`s statistic'));
  }

  getTicketTypeStatistics() {
    this.dashboardService.getTicketTypeStatistic(this.projectId)
      .pipe(first())
      .subscribe(statistics => {
        statistics.forEach(statistic => {
          this.radarChartLabels.push(statistic.name);
          this.radarChartData[0].data.push(statistic.count);
        });
      }, e => this.alertService.error('Cannot load ticket`s statistic'));
  }

  updateUserStatistic() {
    this.pieChartLabels = [];
    this.pieChartData = [];
    this.getUserStatistics();
    this.lastUpdatedPie = new Date();
  }

  updateTicketTypeStatistic() {
    this.radarChartLabels = [];
    this.radarChartData = [
      {data: [], label: 'Ticket types'}
    ];
    this.getTicketTypeStatistics();
    this.lastUpdatedRadar = new Date();
  }

  updateDefectsStatistic() {
    this.lastUpdatedLinear = new Date();
    this.lineChartLabels = [];
    this.lineChartData[0].data = [];
    this.lineChartData[1].data = [];
    this.getDefectsStatistic();
  }

  getDefectsStatistic() {
    this.dashboardService.getDefectsStatistic(this.projectId)
      .pipe(first())
      .subscribe(statistics => {
        let keys: string[] = Object.keys(statistics).sort();
        keys.forEach(key => {
          this.lineChartLabels.push(key);
          this.lineChartData[0].data.push(statistics[key].opened);
          this.lineChartData[1].data.push(statistics[key].closed);
        });
      }, e => this.alertService.error('Cannot load defect`s statistic'));
  }

  getSeries() {
    this.dashboardService.getSeries(this.projectId)
      .pipe(first())
      .subscribe(series => {
        this.series = series;
        if (!this.seriesId) {
          this.seriesId = series[0];
        }
        this.getRunData();
      });
  }

  getRunData() {
    this.dashboardService.getRunInfo(this.seriesId)
      .pipe(first())
      .subscribe(runs => {
        this.runs = runs;
        this.getGanttStatistic(runs);
      });
  }

  getGanttStatistic(runs: RunInfo[]) {

    let items = {};
    let rows = {};

    runs.forEach(run => {

      const id = run.ticketId.toString();
      rows[id] = {
        id,
        label: "Ticket " + run.ticketName,
        parentId: run.parentId ? run.parentId.toString() : undefined,
        expanded: true
      };

      items[id] = {
        id,
        label: "Run for " + run.ticketName,
        time: {
          start: run.startedWhen ? new Date(run.startedWhen).getTime() : undefined,
          end: run.completedWhen ? new Date(run.completedWhen).getTime() : undefined
        },
        rowId: id
      };
    });

    console.log("Final: ")
    console.log(rows);
    console.log(items);

    // LEFT SIDE LIST COLUMNS

    const columns = {
      percent: 100,
      resizer: {
        inRealTime: true
      },
      fontSize: 10,
      data: {
        label: {
          id: "label",
          data: "label",
          expander: true,
          isHtml: true,
          width: 150,
          header: {
            content: "Ticket"
          }
        }
      }
    };

    this.config = {
      height: 400,
      headerHeight: 90,
      list: {
        rows,
        columns
      },
      chart: {
        items
      },
      expander: {
        size: 1
      }
    };
  }

  updateRuns() {
    this.router.navigate(["/project/" + this.projectId + "/dashboard"],
      {queryParams: {'seriesId': this.seriesId}}).then(() => {
      window.location.reload();
    });
  }

}
