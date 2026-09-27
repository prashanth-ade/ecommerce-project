package com.ecommerce.controller;

import com.ecommerce.entity.Notification;
import com.ecommerce.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/notifications")
@CrossOrigin
public class AdminNotificationController {

    private final NotificationService notificationService;

    public AdminNotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @PostMapping("/user/{email}")
    public ResponseEntity<Notification> createNotification(
            @PathVariable String email,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam(required = false) String type) {

        return ResponseEntity.ok(
                notificationService.createNotification(
                        email,
                        title,
                        message,
                        type
                )
        );
    }
}