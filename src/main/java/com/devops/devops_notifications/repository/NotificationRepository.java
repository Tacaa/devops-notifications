package com.devops.devops_notifications.repository;

import com.devops.devops_notifications.enumerations.NotificationType;
import com.devops.devops_notifications.model.Notification;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Observed
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByReceiverIdAndNotificationTypeIn(Integer receiverId, List<NotificationType> notificationTypes);

    @Query("SELECT n FROM Notification n WHERE n.id IN :ids")
    List<Notification> findListOfNotifications(@Param("ids") List<Integer> ids);
}
