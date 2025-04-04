package com.devops.devops_notifications.controllers;

import com.devops.devops_notifications.dto.CreateNotificationDTO;
import com.devops.devops_notifications.dto.NotificationDTO;
import com.devops.devops_notifications.dto.ReadNotificationDTO;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    //front
    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationsByUserId(@PathVariable Integer userId) {
        List<Notification> notifications = notificationService.getAllNotificationsByUserId(userId);
        return new ResponseEntity<>(NotificationDTO.fromEntities(notifications), HttpStatus.OK);
    }

    //review i accommodation poziva
    @PostMapping({"/save"})
    public boolean save(@RequestBody CreateNotificationDTO createNotificationDTO){
        return notificationService.save(createNotificationDTO).getId() != null;
    }

    //front
    @PutMapping(value = "/read")
    public ResponseEntity<List<NotificationDTO>> update(@RequestBody List<ReadNotificationDTO> readNotificationDTOs){
        List<Notification> notifications = notificationService.update(readNotificationDTOs);
        return new ResponseEntity<>(NotificationDTO.fromEntities(notifications), HttpStatus.OK);
    }

}
