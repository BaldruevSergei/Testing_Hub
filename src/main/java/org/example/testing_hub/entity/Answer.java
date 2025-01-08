package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean isCorrect;

    @Column(name = "order_column")
    private Integer orderColumn;// Порядок отображения ответа

    @Column(length = 500)
    private String explanation; // Комментарий или объяснение к ответу

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
