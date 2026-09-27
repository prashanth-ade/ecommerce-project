package com.ecommerce.service;

import com.ecommerce.entity.Notification;
import com.ecommerce.entity.User;
import com.ecommerce.repository.NotificationRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository) {

        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // Get all notifications
    public List<Notification> getNotifications(
            String email) {

        User user = getUser(email);

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user);
    }

    // Get unread notifications
    public List<Notification> getUnreadNotifications(
            String email) {

        User user = getUser(email);

        return notificationRepository
                .findByUserAndReadFalseOrderByCreatedAtDesc(user);
    }

    // Get unread notification count
    public long getUnreadCount(String email) {

        User user = getUser(email);

        return notificationRepository
                .countByUserAndReadFalse(user);
    }

    // Create notification
    public Notification createNotification(
            String email,
            String title,
            String message,
            String type) {

        User user = getUser(email);

        if (title == null || title.isBlank()) {
            throw new RuntimeException(
                    "Notification title is required");
        }

        if (message == null || message.isBlank()) {
            throw new RuntimeException(
                    "Notification message is required");
        }

        if (type == null || type.isBlank()) {
            type = "GENERAL";
        }

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type.toUpperCase());
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    // Mark one notification as read
    public Notification markAsRead(
            String email,
            Long notificationId) {

        User user = getUser(email);

        Notification notification =
                notificationRepository
                        .findByIdAndUser(
                                notificationId,
                                user
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        notification.setRead(true);

        return notificationRepository.save(notification);
    }

    // Mark all notifications as read
    public void markAllAsRead(String email) {

        User user = getUser(email);

        List<Notification> notifications =
                notificationRepository
                        .findByUserOrderByCreatedAtDesc(user);

        for (Notification notification : notifications) {
            notification.setRead(true);
        }

        notificationRepository.saveAll(notifications);
    }

    // Delete one notification
    public void deleteNotification(
            String email,
            Long notificationId) {

        User user = getUser(email);

        Notification notification =
                notificationRepository
                        .findByIdAndUser(
                                notificationId,
                                user
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        notificationRepository.delete(notification);
    }

    // Clear all notifications
    public void clearNotifications(String email) {

        User user = getUser(email);

        List<Notification> notifications =
                notificationRepository
                        .findByUserOrderByCreatedAtDesc(user);

        notificationRepository.deleteAll(notifications);
    }
}