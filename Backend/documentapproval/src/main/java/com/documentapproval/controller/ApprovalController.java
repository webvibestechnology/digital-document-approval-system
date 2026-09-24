package com.documentapproval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.documentapproval.dto.ApprovalRequest;
import com.documentapproval.dto.SubmitApprovalRequest;
import com.documentapproval.entity.Approval;
import com.documentapproval.services.ApprovalService;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping("/submit")
    public ResponseEntity<Approval> submitForApproval(@RequestBody SubmitApprovalRequest request) {
        Approval approval = approvalService.submitForApproval(
                request.getDocumentId(), 
                request.getApproverEmail()
        );
        return new ResponseEntity<>(approval, HttpStatus.CREATED);
    }

    @PostMapping("/process")
    public ResponseEntity<Approval> processApproval(
            @RequestBody ApprovalRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        String approverEmail = userDetails.getUsername(); 
        Approval approval = approvalService.processApproval(request, approverEmail);
        return ResponseEntity.ok(approval);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Approval>> getMyPendingApprovals(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        String approverEmail = userDetails.getUsername();
        List<Approval> pendingApprovals = approvalService.getPendingApprovalsByApprover(approverEmail);
        return ResponseEntity.ok(pendingApprovals);
    }

    @GetMapping("/document/{documentId}")
    public ResponseEntity<List<Approval>> getApprovalHistory(@PathVariable Long documentId) {
        List<Approval> history = approvalService.getApprovalHistoryForDocument(documentId);
        return ResponseEntity.ok(history);
    }
}
