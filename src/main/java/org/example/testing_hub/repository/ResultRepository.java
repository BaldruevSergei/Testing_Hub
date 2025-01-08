package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudent_Id(Long studentId); // Поиск результатов по ученику

    List<Result> findByTest_Id(Long testId); // Поиск результатов по тесту
}
