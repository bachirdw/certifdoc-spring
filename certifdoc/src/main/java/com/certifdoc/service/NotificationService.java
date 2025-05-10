package com.certifdoc.service;

import com.certifdoc.entity.NotificationEntity;
import com.certifdoc.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Récupérer toutes les notifications
    public List<NotificationEntity> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Récupérer une notification par ID
    public NotificationEntity getNotificationById(Long idNotification) {
        return notificationRepository.findById(idNotification)
                .orElseThrow(() -> new RuntimeException("❌ Notification introuvable avec l'ID : " + idNotification));
    }

    // Ajouter une notification
    public NotificationEntity addNotification(NotificationEntity notification) {
        return notificationRepository.save(notification);
    }
}