package com.devops.devops_notifications.dto;

import com.devops.devops_notifications.enumerations.NotificationType;
import com.devops.devops_notifications.model.Notification;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Integer id;
    private Integer receiverId;
    private Integer senderId;
    private String title;
    private String content;
    private NotificationType notificationType;
    private boolean read;
    private LocalDate createdAt;

    public static NotificationDTO fromEntity(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .receiverId(notification.getReceiverId())
                .senderId(notification.getSenderId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .notificationType(notification.getNotificationType())
                .read(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public static List<NotificationDTO> fromEntities(List<Notification> notifications) {
        return notifications.stream()
                .map(NotificationDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
