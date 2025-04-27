package com.devops.devops_notifications.controllers;

import com.devops.devops_notifications.dto.*;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.model.NotificationPreference;
import com.devops.devops_notifications.services.NotificationService;
import com.devops.devops_notifications.services.NotificationsPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/notifications-preferences")
public class NotificationPreferencesController {

    @Autowired
    private NotificationsPreferencesService notificationsPreferencesService;

    //front poziva
    @GetMapping(value = "/{userId}")
    public ResponseEntity<NotificationsPreferencesDTO> getAllNotificationsPreferenceByUserId(@PathVariable Integer userId) {
        NotificationPreference notificationPreference = notificationsPreferencesService.getAllNotificationsPreferencesByUserId(userId);
        return new ResponseEntity<>(NotificationsPreferencesDTO.fromEntity(notificationPreference), HttpStatus.OK);
    }

    //user poziva
    @PostMapping
    public boolean saveNotificationsPreferences(@RequestBody CreateNotificationsPreferencesDTO createNotificationsPreferencesDTO){
        NotificationPreference notificationPreference = notificationsPreferencesService.save(createNotificationsPreferencesDTO.getUserId(), createNotificationsPreferencesDTO.isGuest());
        return notificationPreference.getId() != null;
    }

    //front poziva
    @PutMapping
    public ResponseEntity<NotificationsPreferencesDTO> update(@RequestBody NotificationsPreferencesDTO notificationsPreferencesDTO){
        NotificationPreference notificationPreference = notificationsPreferencesService.update(notificationsPreferencesDTO);
        return new ResponseEntity<>(NotificationsPreferencesDTO.fromEntity(notificationPreference), HttpStatus.OK);
    }

}
