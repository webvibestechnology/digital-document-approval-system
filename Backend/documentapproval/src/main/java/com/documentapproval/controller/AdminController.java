package com.documentapproval.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.documentapproval.dto.DashboardStatsResponse;
import com.documentapproval.dto.UserResponse;
import com.documentapproval.dto.repository.UserRepository;
import com.documentapproval.repository.ApprovalRepository;
import com.documentapproval.repository.DocumentRepository;
import com.documentapproval.services.UserService;

@RestController
@RequestMapping("/api/admin") 
@PreAuthorize("hasRole('ADMIN')") 
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final ApprovalRepository approvalRepository;

    public AdminController(UserService userService, 
                           UserRepository userRepository,
                           DocumentRepository documentRepository, 
                           ApprovalRepository approvalRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.approvalRepository = approvalRepository;
    }

    /**
     * 
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * 
     * 
     */
    @GetMapping("/dashboard-stats")
    public ResponseEntity<DashboardStatsResponse> getDashboardStats()
    {
        long totalUsers = userRepository.count();
        long totalDocuments = documentRepository.count();
        
        long pendingApprovals = approvalRepository.countByStatus("PENDING"); 

        DashboardStatsResponse stats = new DashboardStatsResponse(totalUsers, totalDocuments, pendingApprovals);
        return ResponseEntity.ok(stats);
    }
}


