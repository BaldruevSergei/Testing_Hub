package org.example.testing_hub.controller;

import org.example.testing_hub.entity.TestStatistics;
import org.example.testing_hub.service.TestStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-statistics")
public class TestStatisticsController {

    @Autowired
    private TestStatisticsService testStatisticsService;

    @GetMapping("/{id}")
    public TestStatistics getTestStatisticsById(@PathVariable Long id) {
        return testStatisticsService.getStatisticsById(id);
    }

    @PostMapping
    public TestStatistics createOrUpdateStatistics(@RequestBody TestStatistics testStatistics) {
        return testStatisticsService.createOrUpdateStatistics(testStatistics);

    }

    @DeleteMapping("/{id}")
    public void deleteStatistics(@PathVariable Long id) {
        testStatisticsService.deleteStatistics(id);
    }
}
