import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Ticket} from "../model/ticket";

@Injectable({providedIn: 'root'})
export class TicketService {
  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<Ticket[]>('api/tickets');
  }

  getAllEpics() {
    return this.http.get<Ticket[]>('api/tickets/epics');
  }

  createTicket(ticket: Ticket) {
    return this.http.post<Ticket>('/api/tickets', ticket);
  }

  update(ticket: Ticket) {
    return this.http.put<Ticket>('/api/tickets/' + ticket.id, ticket);
  }

  getTicketById(id: number) {
    return this.http.get<Ticket>('api/tickets/' + id);
  }

}
