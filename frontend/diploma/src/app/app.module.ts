import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthGuard} from "./service/auth.guard";
import {AuthenticationService} from "./service/authentication.service";
import {JwtInterceptor} from "./service/jwt.interceptor";
import {ErrorInterceptor} from "./service/error.interceptor";
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {UserService} from "./service/user.service";
import {AlertService} from "./service/alert.service";
import {TicketComponent} from './ticket/ticket/ticket.component';
import {TicketAddComponent} from './ticket/ticket.add/ticket.add.component';
import {TicketEditComponent} from './ticket/ticket.edit/ticket.edit.component';
import {NgxSpinnerModule} from 'ngx-spinner';
import {TicketService} from "./service/ticket.service";
import {AutocompleteLibModule} from "angular-ng-autocomplete";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    TicketComponent,
    TicketAddComponent,
    TicketEditComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgxSpinnerModule,
    AutocompleteLibModule
  ],
  providers: [
    AuthGuard,
    AuthenticationService,
    UserService,
    AlertService,
    TicketService,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
