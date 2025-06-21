package com.devops.devops_notifications.controllers;

import com.devops.devops_notifications.dto.CreateNotificationDTO;
import com.devops.devops_notifications.dto.NotificationDTO;
import com.devops.devops_notifications.dto.ReadNotificationDTO;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    //front
    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationsByUserId(@PathVariable Integer userId) {
        log.info("Fetching notifications for user ID: {}", userId);

        List<Notification> notifications = notificationService.getAllNotificationsByUserId(userId);
        log.info("Fetched {} notifications for user ID: {}", notifications.size(), userId);

        return new ResponseEntity<>(NotificationDTO.fromEntities(notifications), HttpStatus.OK);
    }

    //review i accommodation poziva
    @PostMapping({"/save"})
    public boolean save(@RequestBody CreateNotificationDTO createNotificationDTO){
        log.info("Saving notification: {}", createNotificationDTO);

        boolean success = notificationService.save(createNotificationDTO).getId() != null;
        if (success) {
            log.info("Notification successfully saved for receiver ID: {}", createNotificationDTO.getReceiverId());
        } else {
            log.warn("Failed to save notification: {}", createNotificationDTO);
        }

        return success;
    }

    //front
    @PutMapping(value = "/read")
    public ResponseEntity<List<NotificationDTO>> update(@RequestBody List<ReadNotificationDTO> readNotificationDTOs){
        log.info("Marking {} notifications as read", readNotificationDTOs.size());

        List<Notification> notifications = notificationService.update(readNotificationDTOs);
        log.info("Updated {} notifications as read", notifications.size());

        return new ResponseEntity<>(NotificationDTO.fromEntities(notifications), HttpStatus.OK);
    }

}
