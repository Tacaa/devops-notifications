package com.devops.devops_notifications.repository;

import com.devops.devops_notifications.model.NotificationPreference;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;

@Observed
public interface NotificationsPreferencesRepository extends JpaRepository<NotificationPreference, Integer> {

    NotificationPreference findByUserId(Integer userId);

}
