package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student; // Ученик, оставивший обратную связь

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test; // Тест, о котором оставлена обратная связь

    @Column(nullable = false)
    private String message; // Текст обратной связи

    @Column(nullable = false)
    private Integer rating; // Рейтинг (например, от 1 до 5)
}
