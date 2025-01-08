package org.example.testing_hub.repository;

import org.example.testing_hub.entity.TestVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestVersionRepository extends JpaRepository<TestVersion, Long> {
    List<TestVersion> findByTest_Id(Long testId);
}
