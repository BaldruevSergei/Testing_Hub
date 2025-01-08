package org.example.testing_hub.repository;

import org.example.testing_hub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByRole(String role);

    List<User> findByClassEntity_Id(Long classId); // Поиск учеников по классу
}
