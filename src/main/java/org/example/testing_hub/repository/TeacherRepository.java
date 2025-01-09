package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Teacher;
import org.example.testing_hub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);

    // Проверить существование учителя  по логину
    boolean existsByLogin(String login);


    Optional<? extends User> findByLogin(String login);
}
