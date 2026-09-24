import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/autho';

export const ErrorInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return next(req).pipe(
    
    catchError((error: HttpErrorResponse) => {
      
      if (error.status === 401) {
        authService.e.logout();
        {localStorage.clear();

        }       
        router.navigate(['/login']); 
        
      }
      
      else if (error.status === 403) {
        alert(''); 
      }

      return throwError(() => error);
    })
  );
};


