package com.documentapproval.dto;

import com.documentapproval.entity.DocumentStatus;

import lombok.Data;

@Data
public class DocumentResponse {
public Long id;
public String title;
public String description;
public String states;
public String uploadedbyUsername;
public String filedpath;
public String createdat;
public void setId(Long id2) {
	// TODO Auto-generated method stub
	
}
public void setTitle(String title2) {
	// TODO Auto-generated method stub
	
}
public void setFilePath(String filePath) {
	// TODO Auto-generated method stub
	
}
public void setStatus(DocumentStatus status) {
	// TODO Auto-generated method stub
	
}
public void setUploaderName(String name) {
	// TODO Auto-generated method stub
	
}
}
