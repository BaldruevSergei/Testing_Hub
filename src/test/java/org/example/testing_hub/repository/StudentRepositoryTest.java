package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertFalse;

@SpringBootTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testFindByClassName() {
        List<Student> students = studentRepository.findByClassName("9A");
        assertFalse(students.isEmpty());
    }

    @Test
    public void testFindByFirstName() {
        List<Student> students = studentRepository.findByFirstName("John");
        assertFalse(students.isEmpty());
    }

    @Test
    public void testFindByFullNameAndClass() {
        List<Student> students = studentRepository.findByFullNameAndClass("John", "Doe", "9A");
        assertFalse(students.isEmpty());
    }


}
