package com.devops.devops_notifications.dto;

import com.devops.devops_notifications.enumerations.NotificationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNotificationDTO {
    private Integer receiverId;
    private Integer senderId;
    private String title;
    private String content;
    private NotificationType notificationType;
}
