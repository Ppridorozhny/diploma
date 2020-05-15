import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Ticket} from "../model/ticket";
import {Status} from "../model/status";
import {ChangeStatus} from "../model/changeStatus";
import {TicketType} from "../model/ticket.type";

@Injectable({providedIn: 'root'})
export class TicketService {
  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<Ticket[]>('api/tickets');
  }

  getAllByProjectId(projectId: number) {
    return this.http.get<Ticket[]>('api/tickets/project/' + projectId);
  }

  getAllEpics() {
    return this.http.get<Ticket[]>('api/tickets/epics');
  }

  createTicket(ticket: Ticket, parentId: number) {
    let params = new HttpParams();
    if (parentId) {
      params = params.set('parentTicketId', parentId.toString());
    }
    return this.http.post<Ticket>('/api/tickets', ticket, {params: params});
  }

  update(ticket: Ticket) {
    return this.http.put<Ticket>('/api/tickets/' + ticket.id, ticket);
  }

  getTicketById(id: number) {
    return this.http.get<Ticket>('api/tickets/' + id);
  }

  getAvailableStatuses(currentStatus: Status) {
    let params = new HttpParams().set('currentStatus', currentStatus.toString())
    return this.http.get<Status[]>('api/dictionary/available-statuses', {params: params});
  }

  changeStatus(changeStatus: ChangeStatus) {
    return this.http.post<Ticket>('/api/tickets/change-status', changeStatus);
  }

  delete(id: number) {
    return this.http.delete<any>('api/tickets/' + id);
  }

  getAvailableTypes(type: TicketType) {
    let params = new HttpParams().set('currentType', type.toString())
    return this.http.get<TicketType[]>('api/dictionary/available-types', {params: params});
  }

}
