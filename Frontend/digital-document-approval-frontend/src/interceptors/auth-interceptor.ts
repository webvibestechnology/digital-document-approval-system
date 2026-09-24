import { HttpInterceptorFn, HttpRequest, HttpHandlerFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const AuthInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const authService = inject(AuthService);
  const token = authService.getToken(); 

  if (token) {
    const modifiedReq = req.clone({
      setHeaders: {
        // येथे बॅकटिक्स ( ` ) वापरले आहेत हे सुनिश्चित करा
        Authorization: `Bearer ${token}` 
      }
    });
    return next(modifiedReq);
  }

  return next(req);
};
