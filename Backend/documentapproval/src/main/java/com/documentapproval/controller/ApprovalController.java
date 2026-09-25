package com.documentapproval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.documentapproval.dto.ApprovalRequest;
import com.documentapproval.entity.Approval;
import com.documentapproval.services.ApprovalService;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping("/submit")
    public ResponseEntity<Approval> submitForApproval(
            @RequestParam Long documentId,
            @RequestParam String approverEmail) {
        return ResponseEntity.ok(approvalService.submitForApproval(documentId, approverEmail));
    }

    @PostMapping("/process")
    public ResponseEntity<Approval> processApproval(
            @RequestBody ApprovalRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(approvalService.processApproval(request, userDetails.getUsername()));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Approval>> getPendingApprovals(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(approvalService.getPendingApprovalsByApprover(userDetails.getUsername()));
    }

    @GetMapping("/document/{documentId}")
    public ResponseEntity<List<Approval>> getApprovalHistory(@PathVariable Long documentId) {
        return ResponseEntity.ok(approvalService.getApprovalHistoryForDocument(documentId));
    }
}
