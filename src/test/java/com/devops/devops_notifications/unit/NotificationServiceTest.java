package com.devops.devops_notifications.unit;

import com.devops.devops_notifications.dto.CreateNotificationDTO;
import com.devops.devops_notifications.dto.ReadNotificationDTO;
import com.devops.devops_notifications.enumerations.NotificationType;
import com.devops.devops_notifications.model.Notification;
import com.devops.devops_notifications.model.NotificationPreference;
import com.devops.devops_notifications.repository.NotificationRepository;
import com.devops.devops_notifications.repository.NotificationsPreferencesRepository;
import com.devops.devops_notifications.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

  @InjectMocks
  private NotificationService notificationService;

  @Mock
  private NotificationRepository notificationRepository;

  @Mock
  private NotificationsPreferencesRepository preferencesRepository;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllNotificationsByUserId_withPreferences() {
    int userId = 1;

    NotificationPreference pref = new NotificationPreference();
    pref.setUserId(userId);
    pref.setHostResponseEnabled(true);
    pref.setReservationRequestEnabled(false);
    pref.setReservationCancelationEnabled(true);
    pref.setAccommodationReviewEnabled(false);
    pref.setHostReviewEnabled(true);

    List<NotificationType> expectedTypes = Arrays.asList(
        NotificationType.HOST_RESPONSE,
        NotificationType.RESERVATION_CANCELATION,
        NotificationType.HOST_REVIEW
    );

    List<Notification> expectedNotifications = List.of(
        Notification.builder().id(1).receiverId(userId).notificationType(NotificationType.HOST_RESPONSE).build(),
        Notification.builder().id(2).receiverId(userId).notificationType(NotificationType.HOST_REVIEW).build()
    );

    when(preferencesRepository.findByUserId(userId)).thenReturn(pref);
    when(notificationRepository.findByReceiverIdAndNotificationTypeIn(userId, expectedTypes)).thenReturn(expectedNotifications);

    List<Notification> result = notificationService.getAllNotificationsByUserId(userId);

    assertEquals(2, result.size());
    verify(preferencesRepository).findByUserId(userId);
    verify(notificationRepository).findByReceiverIdAndNotificationTypeIn(userId, expectedTypes);
  }

  @Test
  void testGetAllNotificationsByUserId_noPreferences() {
    int userId = 2;

    when(preferencesRepository.findByUserId(userId)).thenReturn(null);

    List<Notification> result = notificationService.getAllNotificationsByUserId(userId);

    assertTrue(result.isEmpty());
    verify(preferencesRepository).findByUserId(userId);
    verify(notificationRepository, never()).findByReceiverIdAndNotificationTypeIn(anyInt(), anyList());
  }

  @Test
  void testSaveNotification() {
    CreateNotificationDTO dto = CreateNotificationDTO.builder()
        .receiverId(1)
        .senderId(2)
        .title("Test Title")
        .content("Test Content")
        .notificationType(NotificationType.HOST_RESPONSE)
        .build();

    Notification savedNotification = Notification.builder()
        .id(1)
        .receiverId(dto.getReceiverId())
        .senderId(dto.getSenderId())
        .title(dto.getTitle())
        .content(dto.getContent())
        .notificationType(dto.getNotificationType())
        .read(false)
        .createdAt(LocalDate.now())
        .build();

    when(notificationRepository.save(any(Notification.class))).thenReturn(savedNotification);

    Notification result = notificationService.save(dto);

    assertNotNull(result);
    assertEquals(dto.getReceiverId(), result.getReceiverId());
    assertEquals(dto.getTitle(), result.getTitle());
    verify(notificationRepository).save(any(Notification.class));
  }

  @Test
  void testUpdateNotifications() {
    ReadNotificationDTO dto1 = new ReadNotificationDTO(1, true);
    ReadNotificationDTO dto2 = new ReadNotificationDTO(2, false);

    List<ReadNotificationDTO> dtos = List.of(dto1, dto2);

    Notification n1 = Notification.builder().id(1).read(false).build();
    Notification n2 = Notification.builder().id(2).read(true).build();

    List<Notification> dbNotifications = new ArrayList<>(List.of(n1, n2));

    when(notificationRepository.findListOfNotifications(List.of(1, 2))).thenReturn(dbNotifications);
    when(notificationRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

    List<Notification> result = notificationService.update(dtos);

    assertEquals(2, result.size());
    assertTrue(result.stream().anyMatch(n -> n.getId() == 1 && n.isRead()));
    assertTrue(result.stream().anyMatch(n -> n.getId() == 2 && !n.isRead()));
    verify(notificationRepository).findListOfNotifications(List.of(1, 2));
    verify(notificationRepository).saveAll(anyList());
  }
}
