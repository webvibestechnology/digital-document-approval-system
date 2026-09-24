import { Routes } from '@angular/router';

import { LoginComponent } from './app/components/login/login.component';
import { RegisterComponent } from './app/components/register/register.component';
import { DashboardComponent } from './app/components/dashboard/dashboard.component';
import { DocumentComponent } from './app/components/document/document.component';
import { ApprovalComponent } from './app/components/approval/approval.component';
import { NotificationComponent } from './app/components/notification/notification.component';
import { ProfileComponent } from './app/components/profile/profile.component';

import { AuthGuard } from './Gaurd/auth-gaurd'; 

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'documents', component: DocumentComponent, canActivate: [AuthGuard] },
  { path: 'approvals', component: ApprovalComponent, canActivate: [AuthGuard] },
  { path: 'notifications', component: NotificationComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },

  { path: '**', redirectTo: 'login' }
];

