package com.ecommerce.controller;

import com.ecommerce.entity.Notification;
import com.ecommerce.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    // Get all notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                notificationService.getNotifications(email)
        );
    }

    // Get unread notifications
    @GetMapping("/unread")
    public ResponseEntity<List<Notification>>
    getUnreadNotifications(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                notificationService
                        .getUnreadNotifications(email)
        );
    }

    // Get unread count
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                notificationService
                        .getUnreadCount(email)
        );
    }

    // Mark one notification as read
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                notificationService
                        .markAsRead(email, id)
        );
    }

    // Mark all notifications as read
    @PutMapping("/read-all")
    public ResponseEntity<String> markAllAsRead(
            Authentication authentication) {

        String email = authentication.getName();

        notificationService.markAllAsRead(email);

        return ResponseEntity.ok(
                "All notifications marked as read"
        );
    }

    // Delete one notification
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        notificationService.deleteNotification(
                email,
                id
        );

        return ResponseEntity.ok(
                "Notification deleted successfully"
        );
    }

    // Clear all notifications
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearNotifications(
            Authentication authentication) {

        String email = authentication.getName();

        notificationService.clearNotifications(email);

        return ResponseEntity.ok(
                "All notifications cleared successfully"
        );
    }
}