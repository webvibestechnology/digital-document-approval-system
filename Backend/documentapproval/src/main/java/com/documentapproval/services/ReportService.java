package com.documentapproval.services;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.documentapproval.entity.DocumentStatus;
import com.documentapproval.repository.DocumentRepository; 
import com.documentapproval.repository.UserRepository;

@Service
public class ReportService {

    @Autowired 
    private DocumentRepository documentRepository; 

    @Autowired 
    private UserRepository userRepository;

    public Map<String, Object> getSummaryReport() {
        Map<String, Object> report = new HashMap<>();
        
        report.put("totalDocuments", documentRepository.count());
        report.put("totalUsers", userRepository.count());
        
        report.put("pendingDocuments", documentRepository.countByStatus(DocumentStatus.PENDING));
        report.put("approvedDocuments", documentRepository.countByStatus(DocumentStatus.APPROVED));
        report.put("rejectedDocuments", documentRepository.countByStatus(DocumentStatus.REJECTED));
        
        return report;
    }
}
