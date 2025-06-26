export interface QueryDto {
  id: number;
  title: string;
  description: string;
  status: 'PENDING' | 'ARCHIVED' | 'TRASH';
  createdAt: string;
  updatedAt: string;
}
