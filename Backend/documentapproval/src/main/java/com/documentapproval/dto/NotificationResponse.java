package com.documentapproval.dto;

import lombok.Data;

@Data
public class NotificationResponse {
public Long id;
public String message;
public Boolean isRead;
public String createdAt;
}


