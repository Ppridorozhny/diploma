import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {FormControl} from "@angular/forms";
import {TicketService} from "../../../service/ticket.service";
import {AlertService} from "../../../service/alert.service";
import {Ticket} from "../../../model/ticket";
import {User} from "../../../model/user";
import {debounceTime, distinctUntilChanged, first} from "rxjs/operators";
import {TICKET_COLUMNS} from "./config/ticket.columns";
import {ProjectService} from "../../../service/project.service";
import {Project} from "../../../model/project";

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket.list.component.html',
  styleUrls: ['./ticket.list.component.css'],
})
export class TicketListComponent implements OnInit {

  state: string = "tickets";
  tickets: Ticket[] = [];
  project: Project;
  projectId: number;
  profile: User;
  keyword = 'name';
  queryField: FormControl = new FormControl();

  page: number = 1;
  itemsPerPage: number = 10;
  maxSize: number = 5;
  numPages: number = 1;
  length: number = 0;

  rows: Array<any> = [];
  columns: Array<any> = [];

  config: any = {
    paging: true,
    sorting: {columns: TICKET_COLUMNS},
    filtering: {filterString: ''},
    className: ['table-striped', 'table-bordered', 'table-hover', 'table-info']
  };

  constructor(private ticketService: TicketService,
              private route: ActivatedRoute,
              private router: Router,
              private spinner: NgxSpinnerService,
              private alertService: AlertService,
              private projectService: ProjectService) {
  }

  ngOnInit() {

    this.spinner.show();

    this.route.params.subscribe(params => {
      this.projectId = params['projectId'];
      this.projectService.getById(this.projectId)
        .pipe(first())
        .subscribe(project => this.project = project,
          e => this.alertService.error('Cannot load project'));
    }, error => {
      this.alertService.error(error, 'Error');
    });

    this.profile = JSON.parse(localStorage.getItem('currentUser'));

    this.initTickets();

    this.onChangeTable(this.config);

    this.queryField.valueChanges.pipe(
      debounceTime(1000),
      distinctUntilChanged()
    ).subscribe(queryField => {
      console.log(queryField);
      this.ticketService.getAllByProjectId(this.projectId).pipe(first())
        .subscribe((tickets) => {
          this.tickets = tickets;
        }, error => {
          this.alertService.error(error, 'Error');
        });
    }, error => {
      this.alertService.error(error, 'Error');
    });
  }

  initTickets() {
    this.columns = TICKET_COLUMNS;

    this.ticketService.getAll().pipe(first())
      .subscribe((tickets) => {
        this.tickets = tickets;
        this.length = this.tickets.length;
        this.onChangeTable(this.config);
        this.spinner.hide();
      }, error => {
        this.alertService.error(error, 'Error');
      });
  }

  openTicket(ticket: Ticket) {
    this.router.navigate(["/project/" + this.projectId + "/ticket/" + ticket.id])
  }

  changePage(page: any, data: Array<any> = this.tickets): Array<any> {
    let start = (page.page - 1) * page.itemsPerPage;
    let end = page.itemsPerPage > -1 ? (start + page.itemsPerPage) : data.length;
    return data.slice(start, end);
  }

  changeSort(data: any, config: any): any {
    if (!config.sorting) {
      return data;
    }

    let columns = this.config.sorting.columns || [];
    let columnName: string = void 0;
    let sort: string = void 0;

    for (let i = 0; i < columns.length; i++) {
      if (columns[i].sort !== '' && columns[i].sort !== false && columns[i].sort !== null) {
        columnName = columns[i].name;
        sort = columns[i].sort;
      }
    }

    if (!columnName) {
      return data;
    }

    return data.sort((previous: any, current: any) => {
      if (previous[columnName] > current[columnName]) {
        return sort === 'desc' ? -1 : 1;
      } else if (previous[columnName] < current[columnName]) {
        return sort === 'asc' ? -1 : 1;
      }
      return 0;
    });
  }

  changeFilter(data: any, config: any): any {
    let filteredData: Array<any> = data;

    this.columns.forEach((column: any) => {
      if (column.filtering) {
        filteredData = filteredData.filter((item: any) => {
          if (item[column.name] == null)
            item[column.name] = "";

          return item[column.name].match(column.filtering.filterString);
        });
      }
    });

    if (!config.filtering) {
      return filteredData;
    }

    if (config.filtering.columnName) {
      return filteredData.filter((item: any) =>
        item[config.filtering.columnName].match(this.config.filtering.filterString));
    }

    let tempArray: Array<any> = [];
    filteredData.forEach((item: any) => {
      let flag = false;

      this.columns.forEach((column: any) => {
        if (item[column.name].toString().match(this.config.filtering.filterString)) {
          flag = true;
        }
      });


      if (flag) {
        tempArray.push(item);
      }
    });
    filteredData = tempArray;

    return filteredData;
  }

  onChangeTable(config: any, page: any = {page: this.page, itemsPerPage: this.itemsPerPage}): any {
    if (config.filtering) {
      Object.assign(this.config.filtering, config.filtering);
    }

    if (config.sorting) {
      Object.assign(this.config.sorting, config.sorting);
    }

    let filteredData = this.changeFilter(this.tickets, this.config);
    let sortedData = this.changeSort(filteredData, this.config);
    this.rows = page && config.paging ? this.changePage(page, sortedData) : sortedData;
    this.length = sortedData.length;
  }

  onCellClick(data: any): any {
    this.openTicket(data.row);
  }

  createTicket() {
    this.router.navigate(["/project/" + this.projectId + "/ticket/create"]);
  }

}
