package com.devops.devops_notifications.model;

import com.devops.devops_notifications.enumerations.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "is_guest", nullable = false)
    private boolean isGuest;

    @Column(name = "reservation_request_enabled", nullable = false)
    private boolean reservationRequestEnabled;

    @Column(name = "reservation_cancelation_enabled", nullable = false)
    private boolean reservationCancelationEnabled;

    @Column(name = "host_review_enabled", nullable = false)
    private boolean hostReviewEnabled;

    @Column(name = "accommodation_review_enabled", nullable = false)
    private boolean accommodationReviewEnabled;

    @Column(name = "host_response_enabled", nullable = false)
    private boolean hostResponseEnabled;
}
