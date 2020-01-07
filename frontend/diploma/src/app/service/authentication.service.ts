import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {UserLoginDTO} from "../model/user.login";
import {UserDetails} from "../model/user.details";
import {map} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  public currentUser: Observable<UserDetails>;
  private currentUserSubject: BehaviorSubject<UserDetails>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<UserDetails>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  login(account: UserLoginDTO) {
    return this.http.post<UserDetails>('auth/login', account).pipe(
      map(user => {
        if (user && user.accessToken) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }
        return user;
      })
    );
  }

  logout() {

    this.http.post<any>('auth/logout', null)
      .subscribe(
        () => {},
        () => console.error
      );
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
