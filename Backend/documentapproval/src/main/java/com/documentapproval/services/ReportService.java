package com.documentapproval.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.documentapproval.entity.DocumentStatus;
import com.documentapproval.repository.DocumentRepository;
import com.documentapproval.repository.UserRepository;

@Service
public class ReportService {

    @Autowired private DocumentRepository documentRepository;
    @Autowired private UserRepository userRepository;

    public Map<String, Object> getSummaryReport() {
        return Map.of(
                "totalDocuments", documentRepository.count(),
                "totalUsers", userRepository.count(),
                "pendingDocuments", documentRepository.findByStatus(DocumentStatus.PENDING).size(),
                "approvedDocuments", documentRepository.findByStatus(DocumentStatus.APPROVED).size(),
                "rejectedDocuments", documentRepository.findByStatus(DocumentStatus.REJECTED).size()
        );
    }
}
