import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Commentt} from "../model/commentt";

@Injectable()
export class CommentService {

  constructor(private http: HttpClient) {
  }

  create(comment: Commentt) {
    return this.http.post<Commentt>('/api/comments', comment);
  }

  getAllByTicketId(ticketId: number) {
    return this.http.get<Commentt[]>('api/comments/ticket/' + ticketId);
  }

  delete(id: number) {
    return this.http.delete<any>('/api/comments/' + id);
  }
}
