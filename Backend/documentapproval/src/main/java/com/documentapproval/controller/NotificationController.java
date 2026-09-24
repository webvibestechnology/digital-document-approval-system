package com.documentapproval.controller;

import com.documentapproval.dto.NotificationResponse;
import com.documentapproval.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // 1. GET /api/notifications -> लॉग-इन असलेल्या युजरचे सर्व नोटिफिकेशन्स मिळवणे
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllMyNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        String email = userDetails.getUsername(); // लॉग-इन युजरचा ईमेल आयडी
        List<NotificationResponse> notifications = notificationService.getUserNotifications(email);
        return ResponseEntity.ok(notifications);
    }

    // 2. GET /api/notifications/unread -> फक्त न वाचलेले (Unread) नोटिफिकेशन्स मिळवणे
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationResponse>> getMyUnreadNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        String email = userDetails.getUsername();
        List<NotificationResponse> unreadNotifications = notificationService.getUnreadNotifications(email);
        return ResponseEntity.ok(unreadNotifications);
    }

    // 3. PATCH /api/notifications/{id}/read -> एका विशिष्ट नोटिफिकेशनला 'Read' म्हणून मार्क करणे
    @PatchMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable("id") Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read successfully.");
    }

    // 4. PUT /api/notifications/read-all -> सर्व न वाचलेले नोटिफिकेशन्स एकत्र 'Read' म्हणून मार्क करणे
    @PutMapping("/read-all")
    public ResponseEntity<String> markAllAsRead(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        String email = userDetails.getUsername();
        notificationService.markAllAsRead(email);
        return ResponseEntity.ok("All notifications marked as read.");
    }
}

