package com.documentapproval.services;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.documentapproval.dto.repository.UserRepository;
import com.documentapproval.repository.ApprovalRepository;
import com.documentapproval.repository.DepartmentRepository; 
import com.documentapproval.repository.DocumentRepository;

@Service
public class ReportService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository; 

    public Map<String, Object> getSummaryReport() {
        Map<String, Object> summary = new HashMap<>();

        summary.put("totalUsers", userRepository.count());
        summary.put("totalDepartments", departmentRepository.count());

           summary.put("documentStatusCounts", getDocumentStatusBreakdown());

        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        long approvalsThisMonth = approvalRepository.countByCreatedAtAfter(startOfMonth); 
        summary.put("approvalsThisMonth", approvalsThisMonth);

        return summary;
    }

    public Map<String, Long> getDocumentStatusBreakdown() {
        Map<String, Long> breakdown = new HashMap<>();
        
        breakdown.put("PENDING", documentRepository.countByStatus("PENDING"));
        breakdown.put("APPROVED", documentRepository.countByStatus("APPROVED"));
        breakdown.put("REJECTED", documentRepository.countByStatus("REJECTED"));
        
        return breakdown;
    }
}
