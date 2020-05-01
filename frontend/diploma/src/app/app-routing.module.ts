import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./service/auth.guard";
import {TicketAddComponent} from "./ticket/ticket.add/ticket.add.component";
import {TicketComponent} from "./ticket/ticket/ticket.component";
import {TicketEditComponent} from "./ticket/ticket.edit/ticket.edit.component";


const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'project/:projectId/ticket', component: TicketAddComponent, canActivate: [AuthGuard]},
  {path: 'project/:projectId/ticket/:ticketId', component: TicketComponent, canActivate: [AuthGuard]},
  {path: 'project/:projectId/ticket/:ticketId/edit', component: TicketEditComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},

  // redirect to home
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
