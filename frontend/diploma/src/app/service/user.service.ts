import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user";

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<User[]>('api/users');
  }

  getByUsername(username:string) {
    return this.http.get<User>('/api/users/' + username)
  }

}
