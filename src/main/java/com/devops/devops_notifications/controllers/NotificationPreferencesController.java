package com.devops.devops_notifications.controllers;

import com.devops.devops_notifications.dto.*;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.model.NotificationPreference;
import com.devops.devops_notifications.services.NotificationService;
import com.devops.devops_notifications.services.NotificationsPreferencesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/notifications-preferences")
public class NotificationPreferencesController {

    @Autowired
    private NotificationsPreferencesService notificationsPreferencesService;

    //front poziva
    @GetMapping(value = "/{userId}")
    public ResponseEntity<NotificationsPreferencesDTO> getAllNotificationsPreferenceByUserId(@PathVariable Integer userId) {
        log.info("Fetching notification preferences for user ID: {}", userId);

        NotificationPreference notificationPreference = notificationsPreferencesService.getAllNotificationsPreferencesByUserId(userId);

        log.info("Fetched preferences for user ID: {}", userId);
        return new ResponseEntity<>(NotificationsPreferencesDTO.fromEntity(notificationPreference), HttpStatus.OK);
    }

    //user poziva
    @PostMapping
    public boolean saveNotificationsPreferences(@RequestBody CreateNotificationsPreferencesDTO createNotificationsPreferencesDTO){
        NotificationPreference notificationPreference = notificationsPreferencesService.save(createNotificationsPreferencesDTO.getUserId(), createNotificationsPreferencesDTO.isGuest());

        boolean success = notificationPreference.getId() != null;
        if (success) {
            log.info("Successfully saved preferences for user ID: {}", createNotificationsPreferencesDTO.getUserId());
        } else {
            log.warn("Failed to save preferences for user ID: {}", createNotificationsPreferencesDTO.getUserId());
        }

        return success;
    }

    //front poziva
    @PutMapping
    public ResponseEntity<NotificationsPreferencesDTO> update(@RequestBody NotificationsPreferencesDTO notificationsPreferencesDTO){
        log.info("Updating preferences for user ID: {}", notificationsPreferencesDTO.getUserId());

        NotificationPreference notificationPreference = notificationsPreferencesService.update(notificationsPreferencesDTO);
        log.info("Updated preferences for user ID: {}", notificationsPreferencesDTO.getUserId());

        return new ResponseEntity<>(NotificationsPreferencesDTO.fromEntity(notificationPreference), HttpStatus.OK);
    }

}
