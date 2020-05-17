import {Component, OnInit} from "@angular/core";
import {Project} from "../../model/project";
import {ActivatedRoute} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {AlertService} from "../../service/alert.service";
import {ProjectService} from "../../service/project.service";
import {ChartDataSets, ChartOptions, ChartType, RadialChartOptions} from "chart.js";
import {Color, Label} from "ng2-charts";
import * as pluginDataLabels from 'chartjs-plugin-datalabels';
import {first} from "rxjs/operators";
import {DashboardService} from "../../service/dashboard.service";
import * as pluginAnnotations from 'chartjs-plugin-annotation';

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
    { data: [], label: 'Ticket types' }
  ];
  public radarChartType: ChartType = 'radar';

  constructor(private route: ActivatedRoute,
              private spinner: NgxSpinnerService,
              private alertService: AlertService,
              private projectService: ProjectService,
              private dashboardService: DashboardService,
  ) {
  }

  public lineChartData: ChartDataSets[] = [
    { data: [], label: 'Opened' },
    { data: [], label: 'Closed' }
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
      this.getGanttStatistic();
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
      { data: [], label: 'Ticket types' }
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
        keys.forEach( key => {
          this.lineChartLabels.push(key);
          this.lineChartData[0].data.push(statistics[key].opened);
          this.lineChartData[1].data.push(statistics[key].closed);
        });
      }, e => this.alertService.error('Cannot load defect`s statistic'));
  }

  getGanttStatistic() {
    const iterations = 400;

    // GENERATE SOME ROWS

    const rows = {};
    for (let i = 0; i < iterations; i++) {
      const withParent = i > 0 && i % 2 === 0;
      const id = i.toString();
      rows[id] = {
        id,
        label: "Room " + i,
        parentId: withParent ? (i - 1).toString() : undefined,
        expanded: false
      };
    }

    const dayLen = 24 * 60 * 60 * 1000;

    // GENERATE SOME ROW -> ITEMS

    const items = {};
    for (let i = 0; i < iterations; i++) {
      const id = i.toString();
      const start = new Date().getTime();
      items[id] = {
        id,
        label: "User id " + i,
        time: {
          start: start + i * dayLen,
          end: start + (i + 2) * dayLen
        },
        rowId: id
      };
    }

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

  // GET THE GANTT INTERNAL STATE

  onState(state) {
    this.gstcState = state;

    // YOU CAN SUBSCRIBE TO CHANGES

    this.gstcState.subscribe("config.list.rows", rows => {
      console.log("rows changed", rows);
    });

    this.gstcState.subscribe(
      "config.chart.items.:id",
      (bulk, eventInfo) => {
        if (eventInfo.type === "update" && eventInfo.params.id) {
          const itemId = eventInfo.params.id;
          console.log(
            `item ${itemId} changed`,
            this.gstcState.get("config.chart.items." + itemId)
          );
        }
      },
      { bulk: true }
    );
  }

}
