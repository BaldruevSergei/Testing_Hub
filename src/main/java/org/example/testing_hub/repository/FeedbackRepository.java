package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByTest_Id(Long testId); // Поиск обратной связи по тесту

    List<Feedback> findByStudent_Id(Long studentId); // Поиск обратной связи по ученику
}
