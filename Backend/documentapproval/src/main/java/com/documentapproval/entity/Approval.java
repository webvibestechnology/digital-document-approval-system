package com.documentapproval.entity;

	import jakarta.persistence.*;
	import java.time.LocalDateTime;

	@Entity
	@Table(name = "approvals")
	public class Approval {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String comments;

	    @Column(name = "action_date")
	    private LocalDateTime actionDate;

	    @ManyToOne
	    @JoinColumn(name = "document_id", nullable = false)
	    private Document document;

	    @ManyToOne
	    @JoinColumn(name = "approver_id", nullable = false)
	    private User approver;

	    @Enumerated(EnumType.STRING)
	    private DocumentStatus status;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getComments() {
	        return comments;
	    }

	    public void setComments(String comments) {
	        this.comments = comments;
	    }

	    public LocalDateTime getActionDate() {
	        return actionDate;
	    }

	    public void setActionDate(LocalDateTime actionDate) {
	        this.actionDate = actionDate;
	    }

	    public Document getDocument() {
	        return document;
	    }

	    public void setDocument(Document document) {
	        this.document = document;
	    }

	    public User getApprover() {
	        return approver;
	    }

	    public void setApprover(User document2) {
	        this.approver = document2;
	    }

	    public DocumentStatus getStatus() {
	        return status;
	    }

	    public void setStatus(DocumentStatus status) {
	        this.status = status;
	    }

		public void setApprover(Document document2) {
			// TODO Auto-generated method stub
			
		}
	}


