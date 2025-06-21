package com.devops.devops_notifications.services;

import com.devops.devops_notifications.dto.CreateNotificationDTO;
import com.devops.devops_notifications.dto.ReadNotificationDTO;
import com.devops.devops_notifications.enumerations.NotificationType;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.model.NotificationPreference;
import com.devops.devops_notifications.repository.NotificationRepository;
import com.devops.devops_notifications.repository.NotificationsPreferencesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationsPreferencesRepository notificationsPreferencesRepository;

    public List<Notification> getAllNotificationsByUserId(Integer userId) {
        log.info("Fetching notifications for user ID: {}", userId);
        NotificationPreference preferences = notificationsPreferencesRepository.findByUserId(userId);

        if (preferences == null) {
            log.warn("No notification preferences found for user ID: {}", userId);
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
        log.info("Allowed notification types for user ID {}: {}", userId, notificationTypes);

        return notificationRepository.findByReceiverIdAndNotificationTypeIn(userId, notificationTypes);
    }

    public Notification save(CreateNotificationDTO createNotificationDTO){
        log.info("Saving notification for receiver ID: {}, type: {}",
                createNotificationDTO.getReceiverId(),
                createNotificationDTO.getNotificationType());

        Notification notification = Notification.builder()
               .receiverId(createNotificationDTO.getReceiverId())
               .senderId(createNotificationDTO.getSenderId())
               .title(createNotificationDTO.getTitle())
               .content(createNotificationDTO.getContent())
               .notificationType(createNotificationDTO.getNotificationType())
               .read(false)
               .createdAt(LocalDate.now())
               .build();

        log.info("Notification saved!");
        return this.notificationRepository.save(notification);
    }

    public List<Notification> update(List<ReadNotificationDTO> readNotificationDTOs){
        List<Integer> ids = readNotificationDTOs.stream()
                .map(ReadNotificationDTO::getId)
                .toList();

        log.info("Updating read status for notifications: {}", ids);

        List<Notification> notificationsDB = notificationRepository.findListOfNotifications(ids);

        Map<Integer, ReadNotificationDTO> notificationDTOMap = readNotificationDTOs.stream()
                .collect(Collectors.toMap(ReadNotificationDTO::getId, dto -> dto));

        notificationsDB.forEach(notification -> {
            ReadNotificationDTO matchingNotification = notificationDTOMap.get(notification.getId());
            if (matchingNotification != null) {
                notification.setRead(matchingNotification.isRead());
                log.debug("Notification ID {} marked as read: {}", notification.getId(), matchingNotification.isRead());
            }
        });

        return notificationRepository.saveAll(notificationsDB);
    }
}
