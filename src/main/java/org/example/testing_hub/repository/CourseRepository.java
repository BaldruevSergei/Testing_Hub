package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacher_Id(Long teacherId); // Поиск курсов по преподавателю
}
