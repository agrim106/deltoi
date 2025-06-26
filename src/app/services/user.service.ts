import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  private authUrl = 'http://localhost:8080/api/auth';
  private userUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  signup(data: any): Observable<any> {
    console.log('UserService.signup() - sending request with:', data);

    return this.http.post(`${this.authUrl}/signup`, data).pipe(
      tap((res: any) => {
        console.log('UserService.signup() - received response:', res);
        if (res && res.token) {
          localStorage.setItem('jwt', res.token);
          console.log('JWT token saved to localStorage after signup');
        } else {
          console.warn('No token received in signup response');
        }
      })
    );
  }

  login(data: any): Observable<any> {
    console.log('UserService.login() - sending request with:', data);

    return this.http.post(`${this.authUrl}/login`, data).pipe(
      tap((res: any) => {
        console.log('UserService.login() - received response:', res);
        console.log(res.email);
        if (res && res.token) {
          localStorage.setItem('jwt', res.token);
          localStorage.setItem('user', JSON.stringify(res.user));
          console.log('JWT token saved to localStorage after login');
        } else {
          console.warn('No token received in login response');
        }
      })
    );
  }

  logout(): Observable<any> {
    localStorage.removeItem('jwt');
    console.log('JWT token removed from localStorage');
    return this.http.post(`${this.userUrl}/logout`, {});
  }

  getProfile(): Observable<any> {
    console.log('Fetching user profile');
    return this.http.get(`${this.userUrl}/profile`);
  }
}
