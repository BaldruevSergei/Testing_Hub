package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    /**
     * Поиск учителей по имени
     */
    List<Teacher> findByFirstName(String firstName);

    /**
     * Поиск учителей по фамилии
     */
    List<Teacher> findByLastName(String lastName);

    /**
     * Поиск учителей по предмету
     */
    List<Teacher> findBySubject(String subject);

    /**
     * Поиск учителей по должности
     */
    List<Teacher> findByPosition(String position);

    /**
     * Поиск учителя по email
     */
    Optional<Teacher> findByEmail(String email);

    /**
     * Проверка существования учителя по email
     */
    boolean existsByEmail(String email);
}
