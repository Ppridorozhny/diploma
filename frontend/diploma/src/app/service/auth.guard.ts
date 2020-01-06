import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {UserDetails} from "../model/user.details";

@Injectable()
export class AuthGuard implements CanActivate {

  currentUser: UserDetails;

  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    let storage = localStorage.getItem('currentUser');
    if (storage) {
      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

    if (! (this.currentUser && this.currentUser.accessToken) ) {
      this.router.navigate(['/login']).then(() => console.log("Please login"));
      return false;
    }

    return true;
  }
}
