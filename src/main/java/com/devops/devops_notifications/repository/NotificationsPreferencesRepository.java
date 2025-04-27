package com.devops.devops_notifications.repository;

import com.devops.devops_notifications.model.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsPreferencesRepository extends JpaRepository<NotificationPreference, Integer> {

    NotificationPreference findByUserId(Integer userId);

}
