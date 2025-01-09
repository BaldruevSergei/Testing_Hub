package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action; // Действие, например, "Создан тест", "Пройден тест"

    @Column(nullable = false)
    private LocalDateTime timestamp; // Время события

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Teacher user; // Пользователь, совершивший действие
}
