package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Поиск вопросов по ID группы вопросов
    List<Question> findByQuestionGroup_Id(Long groupId);
}
