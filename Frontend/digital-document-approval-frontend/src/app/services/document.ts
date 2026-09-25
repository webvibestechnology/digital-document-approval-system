import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Document, DocumentRequest } from '../models/document';

@Injectable({
  providedIn: 'root',
})
export class DocumentService {
  private apiUrl = 'http://localhost:8080/api/documents';

  constructor(private http: HttpClient) {}

  uploadDocument(title: string, description: string, file: File): Observable<Document> {
    const formData = new FormData();
    formData.append('data', new Blob([JSON.stringify({ title, description })], { type: 'application/json' }));
    formData.append('file', file, file.name);
    return this.http.post<Document>(`${this.apiUrl}/upload`, formData);
  }

  getMyDocuments(): Observable<Document[]> {
    return this.http.get<Document[]>(`${this.apiUrl}/my`);
  }

  getAllDocuments(): Observable<Document[]> {
    return this.http.get<Document[]>(this.apiUrl);
  }

  deleteDocument(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  downloadDocument(id: number): void {
    window.open(`${this.apiUrl}/${id}/download`, '_blank');
  }
}
