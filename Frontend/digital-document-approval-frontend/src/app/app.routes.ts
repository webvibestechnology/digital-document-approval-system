import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login';
import { RegisterComponent } from './components/register/register';
import { DashboardComponent } from './components/dashboard/dashboard';
import { DocumentComponent } from './components/document/document';
import { ApprovalComponent } from './components/approval/approval';
import { NotificationComponent } from './components/notification/notification';
import { ProfileComponent } from './components/profile/profile';
import { AdminComponent } from './components/admin/admin';
import { authGuard } from './guards/auth-guard';
import { adminGuard } from './guards/admin-guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'documents', component: DocumentComponent, canActivate: [authGuard] },
  { path: 'approvals', component: ApprovalComponent, canActivate: [authGuard] },
  { path: 'notifications', component: NotificationComponent, canActivate: [authGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [authGuard] },
  { path: 'admin', component: AdminComponent, canActivate: [authGuard, adminGuard] },
  { path: '**', redirectTo: 'login' }
];
