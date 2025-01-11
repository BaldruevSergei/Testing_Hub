package org.example.testing_hub.repository;

import org.example.testing_hub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    List<User> findByRole(String role); // Фильтрация по роли
}
