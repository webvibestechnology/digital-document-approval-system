package com.documentapproval.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.documentapproval.dto.ApprovalRequest;
import com.documentapproval.dto.repository.UserRepository;
import com.documentapproval.entity.Approval;
import com.documentapproval.entity.Document;
import com.documentapproval.entity.DocumentStatus;
import com.documentapproval.exception.ResourceNotFoundException;
import com.documentapproval.repository.ApprovalRepository;
import com.documentapproval.repository.DocumentRepository;

@Service
public class ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Approval submitForApproval(Long documentId, String approverEmail)
    {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + documentId));
        
         document = userRepository.findByEmail(approverEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with email: " + approverEmail));

        document.setStatus(DocumentStatus.PENDING);
        documentRepository.save(document);

        Approval approval = new Approval();
        approval.setDocument(document);
        approval.setApprover(document);
        approval.setStatus(DocumentStatus.PENDING);

        notificationService.sendNotification(approverEmail, "तुम्हाला अप्रूव्हलसाठी एक नवीन डॉक्युमेंट आले आहे: " + document.getTitle());

        return approvalRepository.save(approval);
    }

    @Transactional
    public Approval processApproval(ApprovalRequest request, String approverEmail) {
        
           Document approval = approvalRepository.findByDocumentIdAndApproverEmail(request.getDocumentId(), approverEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Approval record not found for this document and approver"));

        DocumentStatus newStatus = DocumentStatus.valueOf(request.getStatus().toUpperCase());
        approval.setStatus(newStatus);

        Document document = approval.getDocument();
        document.setStatus(newStatus);
        documentRepository.save(document);

        String uploaderEmail = ((Document) document.getUser()).getEmail();
        notificationService.sendNotification(uploaderEmail, "तुमच्या '" + document.getTitle() + "' डॉक्युमेंटचे स्टेटस आता " + newStatus + " झाले आहे.");

        return approvalRepository.save(approval);
    }

    public List<Approval> getPendingApprovalsByApprover(String approverEmail)
    {
        return approvalRepository.findByApproverEmailAndStatus(approverEmail, DocumentStatus.PENDING);
    }

    public List<Approval> getApprovalHistoryForDocument(Long documentId) {
        return approvalRepository.findByDocumentId(documentId);
    }
}

