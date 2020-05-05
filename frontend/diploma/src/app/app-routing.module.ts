import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {AuthGuard} from "./service/auth.guard";
import {TicketAddComponent} from "./components/ticket/ticket.add/ticket.add.component";
import {TicketComponent} from "./components/ticket/ticket/ticket.component";
import {TicketEditComponent} from "./components/ticket/ticket.edit/ticket.edit.component";
import {TicketListComponent} from "./components/ticket/list/ticket.list.component";
import {ProjectComponent} from "./components/project/project/project.component";
import {ProjectAddComponent} from "./components/project/project.add/project.add.component";
import {ProjectEditComponent} from "./components/project/project.edit/project.edit.component";
import {ProjectListComponent} from "./components/project/list/project.list.component";


const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'project', component: ProjectListComponent, canActivate: [AuthGuard]},
  {path: 'project/create', component: ProjectAddComponent, canActivate: [AuthGuard]},
  {path: 'project/:projectId', component: ProjectComponent, canActivate: [AuthGuard]},
  {path: 'project/:projectId/edit', component: ProjectEditComponent, canActivate: [AuthGuard]},
  {path: 'project/:projectId/ticket/create', component: TicketAddComponent, canActivate: [AuthGuard]},
  {path: 'project/:projectId/ticket/:ticketId', component: TicketComponent, canActivate: [AuthGuard]},
  {path: 'project/:projectId/ticket/:ticketId/edit', component: TicketEditComponent, canActivate: [AuthGuard]},
  {path: 'project/:projectId/ticket', component: TicketListComponent, canActivate: [AuthGuard]},
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
