import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { QueryService } from '../../../core/services/query.service';
import { QueryDto } from '../../../core/models/query.model';

@Component({
  selector: 'app-trash',
  standalone: true,
  imports: [MatTableModule],
  templateUrl: './trash.component.html',
  styleUrls: ['./trash.component.scss']
})
export class TrashComponent implements OnInit {
  trashedQueries: QueryDto[] = [];

  constructor(private queryService: QueryService) {}

  ngOnInit() {
    this.queryService.getAll().subscribe((queries: QueryDto[]) => {
      this.trashedQueries = queries.filter((q: QueryDto) => q.status === 'TRASH');
    });
  }

  restore(query: QueryDto) {
    this.queryService.restore(query.id).subscribe(() => this.ngOnInit());
  }

  delete(query: QueryDto) {
    this.queryService.deleteFromTrash(query.id).subscribe(() => this.ngOnInit());
  }
}
