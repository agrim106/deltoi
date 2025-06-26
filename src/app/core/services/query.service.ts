import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { QueryDto } from '../models/query.model';

@Injectable({ providedIn: 'root' })
export class QueryService {
  private queryUrl = 'http://localhost:8080/api/queries';

  constructor(private http: HttpClient) {}

  getAll(): Observable<QueryDto[]> {
    return this.http.get<QueryDto[]>(this.queryUrl);
  }

  add(query: Partial<QueryDto>): Observable<QueryDto> {
    return this.http.post<QueryDto>(this.queryUrl, query);
  }

  update(id: number, query: Partial<QueryDto>): Observable<QueryDto> {
    return this.http.put<QueryDto>(`${this.queryUrl}/${id}`, query);
  }

  archive(id: number): Observable<any> {
    return this.http.post(`${this.queryUrl}/archive/${id}`, {});
  }

  restore(id: number): Observable<any> {
    return this.http.post(`${this.queryUrl}/restore/${id}`, {});
  }

  moveToTrash(id: number): Observable<any> {
    return this.http.delete(`${this.queryUrl}/${id}`);
  }

  deleteFromTrash(id: number): Observable<any> {
    return this.http.delete(`${this.queryUrl}/trash/${id}`);
  }

  search(keyword: string): Observable<QueryDto[]> {
    return this.http.get<QueryDto[]>(`${this.queryUrl}/search?keyword=${encodeURIComponent(keyword)}`);
  }
}
