package org.example.testing_hub.controller;

import org.example.testing_hub.entity.TestVersion;
import org.example.testing_hub.service.TestVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-version")
public class TestVersionController {

    @Autowired
    private TestVersionService testVersionService;

    @GetMapping("/by-test/{testId}")
    public List<TestVersion> getVersiontestId(@PathVariable("testId") Long testId) {
        return testVersionService.getVersionsByTestId(testId);
    }
    @GetMapping("/{id}")
    public TestVersion getTestVersionById(@PathVariable Long id) {
        return testVersionService.getVersionById(id);
    }

    @PostMapping
    public TestVersion createTestVersion(@RequestBody TestVersion testVersion) {
        return testVersionService.createVersion(testVersion);
    }

    @DeleteMapping("/{id}")
    public void deleteTestVersion(@PathVariable Long id) {
        testVersionService.deleteVersion(id);
    }
}
