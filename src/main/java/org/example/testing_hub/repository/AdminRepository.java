package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Admin;
import org.example.testing_hub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByDepartment(String department);

    // Проверить существование студента по логину
    boolean existsByLogin(String login);

    Optional<? extends User> findByLogin(String login);
}
