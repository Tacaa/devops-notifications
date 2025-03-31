package com.devops.devops_notifications.services;

import com.devops.devops_notifications.enumerations.NotificationType;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.model.NotificationPreference;
import com.devops.devops_notifications.repository.NotificationRepository;
import com.devops.devops_notifications.repository.NotificationsPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationsPreferencesRepository notificationsPreferencesRepository;

    public List<Notification> getAllNotificationsByUserId(Integer userId) {
        NotificationPreference preferences = notificationsPreferencesRepository.findByUserId(userId);

        if (preferences == null) {
            return Collections.emptyList();
        }

        List<NotificationType> notificationTypes = new ArrayList<>();

        if (preferences.isHostResponseEnabled()) {
            notificationTypes.add(NotificationType.HOST_RESPONSE);
        }

        if (preferences.isReservationRequestEnabled()) {
            notificationTypes.add(NotificationType.RESERVATION_REQUEST);
        }

        if (preferences.isReservationCancelationEnabled()) {
            notificationTypes.add(NotificationType.RESERVATION_CANCELATION);
        }

        if (preferences.isAccommodationReviewEnabled()) {
            notificationTypes.add(NotificationType.ACCOMMODATION_REVIEW);
        }

        if (preferences.isHostReviewEnabled()) {
            notificationTypes.add(NotificationType.HOST_REVIEW);
        }

        return notificationRepository.findByReceiverIdAndNotificationTypeIn(userId, notificationTypes);

    }
}
