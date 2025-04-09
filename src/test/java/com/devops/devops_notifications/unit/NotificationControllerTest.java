package com.devops.devops_notifications.unit;

import com.devops.devops_notifications.controllers.NotificationController;
import com.devops.devops_notifications.dto.CreateNotificationDTO;
import com.devops.devops_notifications.dto.NotificationDTO;
import com.devops.devops_notifications.dto.ReadNotificationDTO;
import com.devops.devops_notifications.enumerations.NotificationType;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

  @Mock
  private NotificationService notificationService;

  @InjectMocks
  private NotificationController notificationController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllNotificationsByUserId_WhenCalled_ReturnsNotificationDTOs() {
    Notification n = Notification.builder()
        .id(1)
        .title("Test")
        .content("Some content")
        .notificationType(NotificationType.HOST_RESPONSE)
        .receiverId(1)
        .senderId(2)
        .createdAt(LocalDate.now())
        .read(false)
        .build();

    when(notificationService.getAllNotificationsByUserId(1)).thenReturn(List.of(n));

    ResponseEntity<List<NotificationDTO>> response = notificationController.getAllNotificationsByUserId(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
  }

  @Test
  void save_WhenValidInput_ReturnsTrue() {
    CreateNotificationDTO dto = new CreateNotificationDTO(1, 2, "Test", "Body", NotificationType.RESERVATION_REQUEST);
    Notification saved = Notification.builder().id(1).build();

    when(notificationService.save(dto)).thenReturn(saved);

    boolean result = notificationController.save(dto);

    assertTrue(result);
  }

  @Test
  void update_WhenValidInput_ReturnsUpdatedNotifications() {
    ReadNotificationDTO dto = new ReadNotificationDTO(1, true);
    Notification updated = Notification.builder().id(1).read(true).build();

    when(notificationService.update(List.of(dto))).thenReturn(List.of(updated));

    ResponseEntity<List<NotificationDTO>> response = notificationController.update(List.of(dto));

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    assertTrue(response.getBody().get(0).isRead());
  }
}
