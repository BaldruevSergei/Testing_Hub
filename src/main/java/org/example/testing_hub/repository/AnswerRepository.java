package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion_Id(Long questionId); // Поиск ответов по вопросу
}
