package org.example.testing_hub.service;

import org.example.testing_hub.entity.Test;
import org.example.testing_hub.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    // Получить все тесты
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // Получить тест по ID
    public Test getTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));
    }

    // Получить тесты по расписанию
    public List<Test> getTestsBySchedule(Long scheduleId) {
        return testRepository.findBySchedule_Id(scheduleId);
    }

    // Создать новый тест
    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    // Обновить существующий тест
    public Test updateTest(Long id, Test updatedTest) {
        Test existingTest = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        existingTest.setName(updatedTest.getName());
        existingTest.setMaxScore(updatedTest.getMaxScore());
        existingTest.setSchedule(updatedTest.getSchedule());

        if (updatedTest.getQuestionGroups() != null) {
            // Очистить существующие группы и добавить новые
            existingTest.getQuestionGroups().clear();
            existingTest.getQuestionGroups().addAll(updatedTest.getQuestionGroups());
        }

        return testRepository.save(existingTest);
    }


    // Удалить тест
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    // Поиск тестов по ключевым словам в названии
    public List<Test> getTestsByNameKeyword(String keyword) {
        return testRepository.findByNameContainingIgnoreCase(keyword);
    }

    // Поиск тестов по временным рамкам
    public List<Test> getTestsByCreatedDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return testRepository.findByCreatedAtBetween(startDate, endDate);
    }

    // Получить все тесты, отсортированные по дате создания
    public List<Test> getTestsSortedByCreatedDate() {
        return testRepository.findAllByOrderByCreatedAtDesc();
    }

    // Поиск тестов с минимальным максимальным баллом
    public List<Test> getTestsByMinimumScore(Integer score) {
        return testRepository.findByMaxScoreGreaterThanEqual(score);
    }
}
