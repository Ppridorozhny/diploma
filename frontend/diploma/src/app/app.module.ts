import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
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
import {TicketComponent} from './components/ticket/ticket/ticket.component';
import {TicketAddComponent} from './components/ticket/ticket.add/ticket.add.component';
import {TicketEditComponent} from './components/ticket/ticket.edit/ticket.edit.component';
import {NgxSpinnerModule} from 'ngx-spinner';
import {TicketService} from "./service/ticket.service";
import {AutocompleteLibModule} from "angular-ng-autocomplete";
import {CommentListComponent} from "./components/ticket/ticket/comment-list/comment-list.component";
import {CommentService} from "./service/comment.service";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    TicketComponent,
    TicketAddComponent,
    TicketEditComponent,
    CommentListComponent,
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
    CommentService,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
