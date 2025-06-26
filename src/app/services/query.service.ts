import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class QueryService {
  private apiUrl = 'http://localhost:8080/api/queries';

  constructor(private http: HttpClient) {}

  getQueries(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getQuery(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  createQuery(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }

  updateQuery(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, data);
  }

  deleteQuery(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  restoreQuery(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/restore/${id}`, {});
  }

  archiveQuery(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/archive/${id}`, {});
  }

  searchQueries(params: any): Observable<any> {
    return this.http.get(`${this.apiUrl}/search`, { params });
  }

  deleteFromTrash(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/trash/${id}`);
  }
}
