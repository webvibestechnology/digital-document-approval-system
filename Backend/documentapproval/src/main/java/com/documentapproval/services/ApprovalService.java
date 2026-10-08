package com.documentapproval.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.documentapproval.dto.ApprovalRequest;
import com.documentapproval.entity.Approval;
import com.documentapproval.entity.Document;
import com.documentapproval.entity.DocumentStatus;
import com.documentapproval.entity.User;
import com.documentapproval.exception.ResourceNotFoundException;
import com.documentapproval.repository.ApprovalRepository;
import com.documentapproval.repository.DocumentRepository;
import com.documentapproval.repository.UserRepository;

@Service
public class ApprovalService {

    @Autowired private ApprovalRepository approvalRepository;
    @Autowired private DocumentRepository documentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private NotificationService notificationService;

    public Approval submitForApproval(Long documentId, String approverEmail) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + documentId));

        User approver = userRepository.findByEmail(approverEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with email: " + approverEmail));

        document.setStatus(DocumentStatus.PENDING);
        documentRepository.save(document);

        Approval approval = new Approval();
        approval.setDocument(document);
        approval.setApprover(approver);
        approval.setStatus(DocumentStatus.PENDING);
        approval.setActionDate(LocalDateTime.now());

        notificationService.createNotification(approver,
                "You have a new document to review: " + document.getTitle());

        return approvalRepository.save(approval);
    }

    public Approval processApproval(ApprovalRequest request, String approverEmail) {
        
        User approver = userRepository.findByEmail(approverEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with email: " + approverEmail));

        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + request.getDocumentId()));

        Optional<Approval> existingApproval = approvalRepository.findByDocumentAndApprover(document, approver);
        
        if (existingApproval.isEmpty()) {
            throw new IllegalArgumentException("या डॉक्युमेंटसाठी तुम्हाला अप्रूव्हर म्हणून नियुक्त केलेले नाही!");
        }

        Approval approval = existingApproval.get();
        
        if (!approval.getApprover().getEmail().equals(approverEmail)) {
            throw new IllegalArgumentException("तुम्ही दुसऱ्या कोणाची अप्रूव्हल रिक्वेस्ट प्रोसेस करू शकत नाही!");
        }

        approval.setStatus(DocumentStatus.valueOf(request.getStatus()));
        approval.setComments(request.getComments());
        approval.setActionDate(LocalDateTime.now());

        document.setStatus(DocumentStatus.valueOf(request.getStatus()));
        documentRepository.save(document);

        notificationService.createNotification(document.getUploadedBy(),
                "Your document '" + document.getTitle() + "' was " + request.getStatus());

        return approvalRepository.save(approval);
    }

	public Object getPendingApprovalsByApprover(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getApprovalHistoryForDocument(Long documentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void processApproval(Long id, String username) {
		// TODO Auto-generated method stub
		
	}
}