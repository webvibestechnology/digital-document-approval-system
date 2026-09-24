import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const RoleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const allowedRoles = route.data['roles'] as Array<string>;
  
  const currentUserRole = authService.getUserRole();

  if (allowedRoles && allowedRoles.includes(currentUserRole)) {
    return true; 
  } else {
    alert('!');
    router.navigate(['/unauthorized']);
    return false;
  }
};


