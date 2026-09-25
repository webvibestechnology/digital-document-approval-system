import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Approval, ApprovalRequest } from '../models/approval';

@Injectable({
  providedIn: 'root',
})
export class ApprovalService {
  private apiUrl = 'http://localhost:8080/api/approvals';

  constructor(private http: HttpClient) {}

  getPendingApprovals(): Observable<Approval[]> {
    return this.http.get<Approval[]>(`${this.apiUrl}/pending`);
  }

  processApproval(request: ApprovalRequest): Observable<Approval> {
    return this.http.post<Approval>(`${this.apiUrl}/process`, request);
  }

  submitForApproval(documentId: number, approverEmail: string): Observable<Approval> {
    const params = new HttpParams()
      .set('documentId', documentId.toString())
      .set('approverEmail', approverEmail);
    return this.http.post<Approval>(`${this.apiUrl}/submit`, null, { params });
  }
}
