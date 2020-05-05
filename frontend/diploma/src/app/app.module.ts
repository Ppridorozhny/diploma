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
import {TicketListComponent} from "./components/ticket/list/ticket.list.component";
import {Ng2TableModule} from "ng2-table/ng2-table";
import {PaginationModule} from "ngx-bootstrap/pagination";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ProjectComponent} from "./components/project/project/project.component";
import {ProjectAddComponent} from "./components/project/project.add/project.add.component";
import {ProjectEditComponent} from "./components/project/project.edit/project.edit.component";
import {ProjectListComponent} from "./components/project/list/project.list.component";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    TicketComponent,
    TicketAddComponent,
    TicketEditComponent,
    CommentListComponent,
    TicketListComponent,
    ProjectAddComponent,
    ProjectComponent,
    ProjectEditComponent,
    ProjectListComponent
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
    AutocompleteLibModule,
    Ng2TableModule,
    PaginationModule.forRoot(),
    FontAwesomeModule
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
