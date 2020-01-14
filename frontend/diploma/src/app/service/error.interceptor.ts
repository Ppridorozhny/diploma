import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {AuthenticationService} from "./authentication.service";
import {Injectable} from "@angular/core";
import {AlertService} from "./alert.service";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  private errors: Error[];

  constructor(private authenticationService: AuthenticationService,
              private alertService: AlertService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      if (err.status === 401) {
        this.authenticationService.logout();
        this.alertService.error("Session has expired. Please re-login", "Error", true);
      }

      if (err.status === 404 || err.status === 500 || err.status === 400) {
        this.errors = err.error;
        this.errors.forEach(e => {
          this.alertService.error(e.message, "Error");
        })
      }
      setTimeout(() => {
          console.log(err);
        }, 5000
      );
      const error = err.error.message || err.statusText;
      return throwError(error);
    }))
  }
}
