package com.certifdoc.controller;

import com.certifdoc.entity.NotificationEntity;
import com.certifdoc.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationEntityController {

    @Autowired
    private NotificationService notificationService;

    // Récupérer toutes les notifications
    @GetMapping
    public List<NotificationEntity> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // Récupérer une notification par ID
    @GetMapping("/{id}")
    public NotificationEntity getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    // Ajouter une notification
    @PostMapping
    public NotificationEntity addNotification(@RequestBody NotificationEntity notification) {
        return notificationService.addNotification(notification);
    }
}