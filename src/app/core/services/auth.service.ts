import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { UserSignupRequest, UserLoginRequest, UserDto } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users'; // Updated to match backend

  constructor(private http: HttpClient) {}

  signup(data: UserSignupRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, data).pipe(
      tap((res: any) => {
        if (res && res.token) {
          localStorage.setItem('jwt', res.token);
        }
      })
    );
  }

  login(data: UserLoginRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, data).pipe(
      tap((res: any) => {
        if (res && res.token) {
          localStorage.setItem('jwt', res.token);
        }
      })
    );
  }

  logout(): Observable<any> {
    localStorage.removeItem('jwt');
    return this.http.post(`${this.apiUrl}/logout`, {});
  }

  getProfile(): Observable<UserDto> {
    return this.http.get<UserDto>(`${this.apiUrl}/profile`);
  }
}