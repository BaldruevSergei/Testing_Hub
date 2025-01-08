package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient_Id(Long recipientId); // Поиск уведомлений по пользователю
}
