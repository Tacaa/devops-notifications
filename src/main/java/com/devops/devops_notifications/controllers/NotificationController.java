package com.devops.devops_notifications.controllers;

import com.devops.devops_notifications.dto.NotificationDTO;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationsByUserId(@PathVariable Integer userId) {
        List<Notification> notifications = notificationService.getAllNotificationsByUserId(userId);
        return new ResponseEntity<>(NotificationDTO.fromEntities(notifications), HttpStatus.OK);
    }

}
