package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Найти всех студентов по классу
    List<Student> findByClassEntity_Grade(String grade);

    // Найти студента по логину
    Optional<Student> findByLogin(String login);

    // Проверить существование студента по логину
    boolean existsByLogin(String login);

    // Найти студентов по имени
    List<Student> findByFirstName(String firstName);

    // Найти студентов по фамилии
    List<Student> findByLastName(String lastName);

    // Найти студентов по имени и фамилии
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);

    // Найти студентов по имени, фамилии и названию класса
    List<Student> findByFirstNameAndLastNameAndClassEntity_Grade(String firstName, String lastName, String grade);
}
