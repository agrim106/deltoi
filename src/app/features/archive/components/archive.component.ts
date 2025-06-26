import { Component, OnInit } from '@angular/core';
import { QueryService } from '../../../core/services/query.service';
import { QueryDto } from '../../../core/models/query.model';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../core/material.module';

@Component({
  selector: 'app-archive',
  standalone: true,
  imports: [CommonModule,
    MaterialModule
  ],
  templateUrl: './archive.component.html',
  styleUrls: ['./archive.component.scss']
})
export class ArchiveComponent implements OnInit {
  archivedQueries: QueryDto[] = [];

  constructor(private queryService: QueryService) {}

  ngOnInit() {
    this.queryService.getAll().subscribe((queries: QueryDto[]) => {
      this.archivedQueries = queries.filter((q: QueryDto) => q.status === 'ARCHIVED');
    });
  }

  restore(query: QueryDto) {
    this.queryService.restore(query.id).subscribe(() => this.ngOnInit());
  }

  moveToTrash(query: QueryDto) {
    this.queryService.moveToTrash(query.id).subscribe(() => this.ngOnInit());
  }
}
