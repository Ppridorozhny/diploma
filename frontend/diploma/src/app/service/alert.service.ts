import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {NavigationStart, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Injectable({ providedIn: 'root' })
export class AlertService {
  private subject = new Subject<any>();
  private keepAfterRouteChange = false;

  constructor(private router: Router, private toastr: ToastrService) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        if (this.keepAfterRouteChange) {
          this.keepAfterRouteChange = false;
          this.showAlert();
        } else {
          this.clear();
        }
      }
    });
  }

  public showAlert() {
    this.subject.asObservable()
      .subscribe(message => {
        switch (message && message.type) {
          case 'success':
            this.showSuccess(message.message, message.title);
            break;
          case 'error':
            this.showError(message.message, message.title);
            break;
        }
      });
  }

  public success(message: string, title: string = 'Success', keepAfterRouteChange = false) {
    this.keepAfterRouteChange = keepAfterRouteChange;
    this.subject.next({ type: 'success', text: message, title: title });
    this.showSuccess(message, title);
  }

  public error(message: string, title: string = 'Error', keepAfterRouteChange = false) {
    this.keepAfterRouteChange = keepAfterRouteChange;
    this.subject.next({ type: 'error', text: message, title: title });
    this.showError(message, title);
  }

  private showError(error: string, title: string) {
    this.toastr.error(error, title, {
      timeOut: 3000,
      positionClass: 'toast-top-right',
      closeButton: true
    });
  }

  private showSuccess(message: string, title: string) {
    this.toastr.info(message, title, {
      timeOut: 3000,
      positionClass: 'toast-top-right',
      closeButton: true
    });
  }

  clear() {
    this.subject.next();
  }
}
