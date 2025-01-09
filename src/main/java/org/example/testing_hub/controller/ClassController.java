package org.example.testing_hub.controller;

import org.example.testing_hub.entity.ClassEntity;
import org.example.testing_hub.entity.Student;
import org.example.testing_hub.service.ClassService;
import org.example.testing_hub.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService; // Добавлена аннотация @Autowired

    /**
     * Получение всех классов.
     */
    @GetMapping
    public List<ClassEntity> getAllClasses() {
        return classService.getAllClasses();
    }

    /**
     * Получение класса по ID.
     */
    @GetMapping("/{id}")
    public ClassEntity getClassById(@PathVariable Long id) {
        return classService.getClassById(id);
    }

    /**
     * Создание нового класса.
     */
    @PostMapping
    public ClassEntity createClass(@RequestBody ClassEntity classEntity) {
        return classService.createClass(classEntity);
    }

    /**
     * Обновление класса по ID.
     */
    @PutMapping("/{id}")
    public ClassEntity updateClass(@PathVariable Long id, @RequestBody ClassEntity updatedClass) {
        return classService.updateClass(id, updatedClass);
    }

    /**
     * Удаление класса по ID.
     */
    @DeleteMapping("/{id}")
    public void deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
    }

    /**
     * Сохранение студентов в указанный класс.
     */
    @PostMapping("/save")
    public String saveClassWithStudents(@RequestBody List<Student> students, @RequestParam String grade) {
        studentService.saveStudentsWithClass(students, grade);
        return "Студенты успешно сохранены в класс " + grade;
    }
}
