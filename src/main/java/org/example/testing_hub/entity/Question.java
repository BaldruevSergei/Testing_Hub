package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false) // Связываем с Test через test_id
    private Test test;

    @ManyToOne
    @JoinColumn(name = "question_group_id", nullable = false)
    private QuestionGroup questionGroup;

    @Column(nullable = false)
    private String type; // Тип вопроса (например, одиночный выбор, множественный выбор и т.д.)


    @Column(nullable = false)
    private String text; // Текст вопроса

    @Column(nullable = false)
    private String correctAnswer; // Правильный ответ
}
