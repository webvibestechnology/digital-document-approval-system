import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  
  const allowedRoles = route.data['roles'] as string[];
  const userRole = authService.getUserRole();
  
  if (allowedRoles && allowedRoles.includes(userRole)) {
    return true;
  }
  
  router.navigate(['/dashboard']);
  return false;
};
