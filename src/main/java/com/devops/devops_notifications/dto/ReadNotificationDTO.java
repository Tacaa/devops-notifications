package com.devops.devops_notifications.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadNotificationDTO {
    private Integer id;
    private boolean read;
}
