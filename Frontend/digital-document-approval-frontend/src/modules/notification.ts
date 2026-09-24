
export interface Notification {
  id: number;
  userId: number;
  message: string;
  isRead: boolean; 
  createdAt: string;
}

export interface NotificationRequest {
  id: number;
  isRead: boolean;
}
