package org.example.testing_hub.repository;

import org.example.testing_hub.entity.QuestionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Long> {
    List<QuestionGroup> findByTest_Id(Long testId);
}
