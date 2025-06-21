package com.devops.devops_notifications.services;

import com.devops.devops_notifications.dto.CreateNotificationDTO;
import com.devops.devops_notifications.dto.NotificationDTO;
import com.devops.devops_notifications.dto.NotificationsPreferencesDTO;
import com.devops.devops_notifications.dto.ReadNotificationDTO;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.model.NotificationPreference;
import com.devops.devops_notifications.repository.NotificationsPreferencesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class NotificationsPreferencesService {

    @Autowired
    private NotificationsPreferencesRepository notificationsPreferencesRepository;

    public NotificationPreference getAllNotificationsPreferencesByUserId(Integer userId) {
        log.info("Fetching notification preferences for user ID: {}", userId);
        return notificationsPreferencesRepository.findByUserId(userId);
    }

    public NotificationPreference save(Integer userId, boolean guest) {
        log.info("Creating default notification preferences for user ID: {}, isGuest: {}", userId, guest);

        NotificationPreference notificationPreference;
        if(guest){
            notificationPreference = NotificationPreference.builder()
                    .userId(userId)
                    .isGuest(true)
                    .hostResponseEnabled(true)
                    .accommodationReviewEnabled(false)
                    .hostReviewEnabled(false)
                    .reservationCancelationEnabled(false)
                    .reservationRequestEnabled(false)
                    .build();
        }else {
            notificationPreference = NotificationPreference.builder()
                    .userId(userId)
                    .isGuest(false)
                    .hostResponseEnabled(false)
                    .accommodationReviewEnabled(true)
                    .hostReviewEnabled(true)
                    .reservationCancelationEnabled(true)
                    .reservationRequestEnabled(true)
                    .build();
        }
        return notificationsPreferencesRepository.save(notificationPreference);
    }

    public NotificationPreference update(NotificationsPreferencesDTO notificationsPreferencesDTO) {
        log.info("Updating notification preferences for user ID: {}", notificationsPreferencesDTO.getUserId());

        NotificationPreference notificationPreference = notificationsPreferencesRepository.findByUserId(notificationsPreferencesDTO.getUserId());
        notificationPreference.setAccommodationReviewEnabled(notificationsPreferencesDTO.isAccommodationReviewEnabled());
        notificationPreference.setHostReviewEnabled(notificationsPreferencesDTO.isHostReviewEnabled());
        notificationPreference.setHostResponseEnabled(notificationsPreferencesDTO.isHostResponseEnabled());
        notificationPreference.setReservationCancelationEnabled(notificationsPreferencesDTO.isReservationCancelationEnabled());
        notificationPreference.setReservationRequestEnabled(notificationsPreferencesDTO.isReservationRequestEnabled());
        return notificationsPreferencesRepository.save(notificationPreference);
    }

}
