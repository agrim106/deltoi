import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QueryService } from './services/query.service';

@Component({
  selector: 'app-query-list',
  templateUrl: './query-list.component.html',
  imports: [CommonModule],
})
export class QueryListComponent implements OnInit {
  queries: any[] = [];

  constructor(private queryService: QueryService) {}

  ngOnInit() {
    this.queryService.getQueries().subscribe((data: any) => {
      this.queries = data;
    });
  }
}
