import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {UserStatistic} from "../model/userStatistic";

@Injectable({providedIn: 'root'})
export class DashboardService {
  constructor(private http: HttpClient) {
  }

  getUserStatistic(projectId: number) {
    let params = new HttpParams().set('projectId', projectId.toString())
    return this.http.get<UserStatistic[]>('api/analytic/user-statistics', {params: params});
  }

}
