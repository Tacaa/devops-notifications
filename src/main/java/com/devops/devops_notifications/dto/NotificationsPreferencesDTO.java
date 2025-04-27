package com.devops.devops_notifications.dto;

import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.model.NotificationPreference;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationsPreferencesDTO {
    private Integer id;
    private Integer userId;
    private boolean isGuest;
    private boolean reservationRequestEnabled;
    private boolean reservationCancelationEnabled;
    private boolean hostReviewEnabled;
    private boolean accommodationReviewEnabled;
    private boolean hostResponseEnabled;

    public static NotificationsPreferencesDTO fromEntity(NotificationPreference preference) {
        return NotificationsPreferencesDTO.builder()
                .id(preference.getId())
                .userId(preference.getUserId())
                .isGuest(preference.isGuest())
                .reservationRequestEnabled(preference.isReservationRequestEnabled())
                .reservationCancelationEnabled(preference.isReservationCancelationEnabled())
                .hostReviewEnabled(preference.isHostReviewEnabled())
                .accommodationReviewEnabled(preference.isAccommodationReviewEnabled())
                .hostResponseEnabled(preference.isHostResponseEnabled())
                .build();
    }
}
