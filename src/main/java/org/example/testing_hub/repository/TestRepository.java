package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    // Найти все тесты, связанные с расписанием
    List<Test> findBySchedule_Id(Long scheduleId);

    // Поиск тестов по ключевым словам в названии
    List<Test> findByNameContainingIgnoreCase(String keyword);

    // Поиск тестов, созданных в определенный временной промежуток
    List<Test> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Получение всех тестов, отсортированных по дате создания
    List<Test> findAllByOrderByCreatedAtDesc();

    // Поиск тестов по минимальному значению максимального балла
    List<Test> findByMaxScoreGreaterThanEqual(Integer score);
}
