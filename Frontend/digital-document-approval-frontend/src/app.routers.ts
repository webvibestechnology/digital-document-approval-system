import { Routes } from '@angular/router';
import { Dashboard } from './app/components/dashboard/dashboard';
import { AuthGuard } from './guard/auth-guard';
import { Login } from './app/components/login/login';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { 
    path: 'dashboard', 
    component: Dashboard, 
    canActivate: [AuthGuard] 
  },
  { path: '**', redirectTo: '/login' }
];

