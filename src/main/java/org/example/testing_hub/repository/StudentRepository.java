package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByLogin(String login); // Проверка существования студента по логину

    @Query("SELECT s FROM Student s WHERE s.classEntity.grade = :className")
    List<Student> findByClassName(@Param("className") String className);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName")
    List<Student> findByFirstName(@Param("firstName") String firstName);

    @Query("SELECT s FROM Student s WHERE s.lastName = :lastName")
    List<Student> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName AND s.lastName = :lastName")
    List<Student> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName AND s.lastName = :lastName AND s.classEntity.grade = :className")
    List<Student> findByFullNameAndClass(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("className") String className);
}
