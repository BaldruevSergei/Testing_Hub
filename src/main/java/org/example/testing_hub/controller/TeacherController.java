package org.example.testing_hub.controller;

import org.example.testing_hub.entity.Teacher;
import org.example.testing_hub.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id)
                .map(teacher -> new ResponseEntity<>(teacher, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        Optional<Teacher> updatedTeacher = teacherService.updateTeacher(id, teacherDetails);

        return updatedTeacher.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search-by-subject")
    public ResponseEntity<List<Teacher>> getTeachersBySubject(@RequestParam String subject) {
        List<Teacher> teachers = teacherService.findTeachersBySubject(subject);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/search-by-position")
    public ResponseEntity<List<Teacher>> getTeachersByPosition(@RequestParam String position) {
        List<Teacher> teachers = teacherService.findTeachersByPosition(position);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/search-by-email")
    public ResponseEntity<Teacher> getTeacherByEmail(@RequestParam String email) {
        return teacherService.findTeacherByEmail(email)
                .map(teacher -> new ResponseEntity<>(teacher, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
