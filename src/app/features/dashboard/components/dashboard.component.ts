import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface QueryDto {
  id: number;
  title: string;
  description: string;
  status: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatIconModule,
    RouterModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  
  queries: QueryDto[] = [];
  userName: string = 'Loading...';

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.loadQueries();
    this.loadUserInfo();
  }

  loadUserInfo() {
    // Assuming token is stored in localStorage from login
    const token = localStorage.getItem('jwt');
    const email = localStorage.getItem('email');
    if (token) {
      // Decode JWT to get email (simplified; use a proper JWT decoder in production)
      const payload = JSON.parse(atob(token.split('.')[1]));
      this.userName = payload.sub || 'Unknown User';
    } else {
      this.userName = 'Guest';
    }
  }

  loadQueries() {
    this.http.get<QueryDto[]>('http://localhost:8080/api/queries', { withCredentials: true }).subscribe({
      next: (data) => this.queries = data,
      error: (err) => console.error('Error fetching queries:', err)
    });
  }

  logout() {
    console.log('Logout clicked');
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  createNewQuery() {
    console.log('Create new query clicked');
    this.router.navigate(['/new-query']);
  }

  archive(query: QueryDto) {
    this.http.post<QueryDto>(`http://localhost:8080/api/queries/archive/${query.id}`, {}).subscribe({
      next: (updatedQuery) => {
        query.status = updatedQuery.status;
      },
      error: (err) => console.error('Error archiving query:', err)
    });
  }

  moveToTrash(query: QueryDto) {
    this.http.delete<QueryDto>(`http://localhost:8080/api/queries/${query.id}`).subscribe({
      next: (updatedQuery) => {
        query.status = updatedQuery.status;
      },
      error: (err) => console.error('Error moving query to trash:', err)
    });
  }
}