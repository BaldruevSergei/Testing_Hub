package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Поиск администратора по имени
     */
    List<Admin> findByFirstName(String firstName);

    /**
     * Поиск администратора по фамилии
     */
    List<Admin> findByLastName(String lastName);

    /**
     * Поиск администратора по департаменту
     */
    List<Admin> findByDepartment(String department);

    /**
     * Поиск администратора по email
     */
    Optional<Admin> findByEmail(String email);

    /**
     * Проверка существования администратора по email
     */
    boolean existsByEmail(String email);
}
