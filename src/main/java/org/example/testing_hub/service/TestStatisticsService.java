package org.example.testing_hub.service;

import org.example.testing_hub.entity.TestStatistics;
import org.example.testing_hub.repository.TestStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestStatisticsService {

    @Autowired
    private TestStatisticsRepository testStatisticsRepository;

    public TestStatistics getStatisticsById(Long id) {
        return testStatisticsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statistics not found"));
    }

    public TestStatistics createOrUpdateStatistics(TestStatistics statistics) {
        return testStatisticsRepository.save(statistics);
    }

    public void deleteStatistics(Long id) {
        testStatisticsRepository.deleteById(id);
    }
}
