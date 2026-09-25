export interface ApprovalDocument {
  id: number;
  title: string;
  description: string;
  status: string;
  uploadedByUsername?: string;
  filePath?: string;
  createdAt?: string;
}

export interface ApprovalUser {
  id: number;
  username: string;
  email: string;
}

export interface Approval {
  id: number;
  document: ApprovalDocument;
  approver: ApprovalUser;
  status: string;
  comments: string;
  actionDate: string;
}

export interface ApprovalRequest {
  documentId: number;
  status: string;
  comments: string;
}
