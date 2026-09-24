package com.documentapproval.dto;

	public class SubmitApprovalRequest {
	    private Long documentId;
	    private String approverEmail;

	    // Getters and Setters
	    public Long getDocumentId() { return documentId; }
	    public void setDocumentId(Long documentId) { this.documentId = documentId; }
	    public String getApproverEmail() { return approverEmail; }
	    public void setApproverEmail(String approverEmail) { this.approverEmail = approverEmail; }
	}


