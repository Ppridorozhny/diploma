import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TicketRelation} from "../model/ticket.relation";

@Injectable({providedIn: 'root'})
export class TicketRelationsService {
  constructor(private http: HttpClient) {
  }

  getRelationsByTicketId(id: number) {
    return this.http.get<TicketRelation[]>('api/ticket-relations/source/' + id);
  }

  create(relation: TicketRelation) {
    return this.http.post<TicketRelation>('/api/ticket-relations', relation);
  }

  delete(id: number) {
    return this.http.delete<any>('api/ticket-relations/' + id);
  }

}
