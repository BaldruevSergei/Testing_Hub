package org.example.testing_hub.controller;

import org.example.testing_hub.entity.ClassEntity;
import org.example.testing_hub.service.ClassService;
import org.example.testing_hub.entity.User;
import org.example.testing_hub.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService classService;
    private StudentService studentService;

    @GetMapping
    public List<ClassEntity> getAllClasses() {
        return classService.getAllClasses();
    }

    @GetMapping("/{id}")
    public ClassEntity getClassById(@PathVariable Long id) {
        return classService.getClassById(id);
    }

    @PostMapping
    public ClassEntity createClass(@RequestBody ClassEntity classEntity) {
        return classService.createClass(classEntity);
    }

    @PutMapping("/{id}")
    public ClassEntity updateClass(@PathVariable Long id, @RequestBody ClassEntity updatedClass) {
        return classService.updateClass(id, updatedClass);
    }

    @DeleteMapping("/{id}")
    public void deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
    }
    @PostMapping("/save")
    public String saveClassWithStudents(@RequestBody List<User> students, @RequestParam String grade) {
        studentService.saveStudentsWithClass(students, grade);
        return "Студенты успешно сохранены в класс " + grade;
    }
}
