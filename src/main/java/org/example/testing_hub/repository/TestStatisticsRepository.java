package org.example.testing_hub.repository;

import org.example.testing_hub.entity.TestStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestStatisticsRepository extends JpaRepository<TestStatistics, Long> {
}
