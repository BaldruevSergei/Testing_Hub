package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message; // Текст уведомления

    @Column(nullable = false)
    private boolean isRead; // Прочитано ли уведомление

    @Column(nullable = false)
    private LocalDateTime createdAt; // Дата создания уведомления

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User recipient; // Получатель уведомления
}
