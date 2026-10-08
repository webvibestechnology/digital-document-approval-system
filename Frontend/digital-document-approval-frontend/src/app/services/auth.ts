import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
 getUserId(): number {
  const userId = localStorage.getItem('userId');
  return userId ? parseInt(userId, 10) : 0;
}

  getUserEmail(): any {
    throw new Error('Method not implemented.');
  }
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}
  login(loginData: LoginRequest): Observable<any> {
    // responseType: 'text' काढून टाकले आहे जेणेकरून अँगुलरला डायरेक्ट JSON डेटा मिळेल
    return this.http.post<any>(`${this.apiUrl}/login`, loginData, {
      headers: { 'Content-Type': 'application/json' }
    });
  }
  
  register(data: RegisterRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data);
  }

  saveToken(token: string, role: string, username: string): void {
    localStorage.setItem('token', token);
    localStorage.setItem('role', role);
    localStorage.setItem('username', username);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getUserRole(): string {
    return localStorage.getItem('role') || '';
  }

  getUsername(): string {
    return localStorage.getItem('username') || '';
  }

  logout(): void {
    localStorage.clear();
  }
}
