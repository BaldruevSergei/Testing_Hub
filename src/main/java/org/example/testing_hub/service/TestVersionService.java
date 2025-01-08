package org.example.testing_hub.service;

import org.example.testing_hub.entity.TestVersion;
import org.example.testing_hub.repository.TestVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestVersionService {

    @Autowired
    private TestVersionRepository testVersionRepository;

    public List<TestVersion> getVersionsByTestId(Long testId) {
        return testVersionRepository.findByTest_Id(testId);
    }

    public TestVersion getVersionById(Long id) {
        return testVersionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestVersion not found"));
    }

    public TestVersion createVersion(TestVersion version) {
        return testVersionRepository.save(version);
    }

    public void deleteVersion(Long id) {
        testVersionRepository.deleteById(id);
    }
}
