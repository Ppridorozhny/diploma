import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {AuthenticationService} from "./authentication.service";
import {Injectable} from "@angular/core";
import {ToastrService} from "ngx-toastr";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  private errors : Error[];

  constructor(private authenticationService: AuthenticationService,
              private toastr: ToastrService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      if (err.status === 401) {
        this.authenticationService.logout();
        this.showError("Session has expired. Please re-login", "Error");
      }

      if (err.status === 404 || err.status === 500 || err.status === 400) {
        this.errors = err.error;
        this.errors.forEach(error => {
          this.showError(error.message, "Error");
        })
      }
      setTimeout(()=>{
        console.log(err);
      }, 3000);
      const error = err.error.message || err.statusText;
      return throwError(error);
    }))
  }

  showError(error: string, title: string) {
    this.toastr.error(error, title, {
      timeOut: 3000,
      positionClass: 'toast-top-right',
      closeButton: true
    });
  }
}
