package org.example.testing_hub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.testing_hub.dto.StudentDTO;
import org.example.testing_hub.entity.Student;
import org.example.testing_hub.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Загрузка списка студентов из локального Excel файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список студентов загружен успешно",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка загрузки файла", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<List<StudentDTO>> uploadStudents(@RequestParam("file") MultipartFile file) {
        try {
            List<StudentDTO> students = studentService.processAndSaveStudents(file.getInputStream());
            return ResponseEntity.ok(students);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @Operation(summary = "Сохранение студентов с указанием класса")
    @PostMapping("/save")
    public ResponseEntity<String> saveStudentsWithClass(@RequestBody List<Student> students, @RequestParam String grade) {
        studentService.saveStudentsWithClass(students, grade);
        return ResponseEntity.ok("Студенты успешно сохранены с классом: " + grade);
    }

    @Operation(summary = "Получить всех студентов")
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.findStudentsByClassName("default"));
    }



    @Operation(summary = "Поиск студентов по имени")
    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<List<Student>> getStudentsByFirstName(@PathVariable String firstName) {
        return ResponseEntity.ok(studentService.findStudentsByFirstName(firstName));
    }

    @Operation(summary = "Поиск студентов по фамилии")
    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<Student>> getStudentsByLastName(@PathVariable String lastName) {
        return ResponseEntity.ok(studentService.findStudentsByLastName(lastName));
    }

    @Operation(summary = "Поиск студентов по имени и фамилии")
    @GetMapping("/fullName")
    public ResponseEntity<List<Student>> getStudentsByFullName(@RequestParam String firstName,
                                                               @RequestParam String lastName) {
        return ResponseEntity.ok(studentService.findStudentsByFullName(firstName, lastName));
    }

    @Operation(summary = "Поиск студентов по классу")
    @GetMapping("/class/{grade}")
    public ResponseEntity<List<Student>> getStudentsByClass(@PathVariable String grade) {
        return ResponseEntity.ok(studentService.findStudentsByClassName(grade));
    }

    @Operation(summary = "Поиск студентов по имени, фамилии и классу")
    @GetMapping("/fullNameAndClass")
    public ResponseEntity<List<Student>> getStudentsByFullNameAndClass(@RequestParam String firstName,
                                                                       @RequestParam String lastName,
                                                                       @RequestParam String grade) {
        return ResponseEntity.ok(studentService.findStudentsByFullNameAndClass(firstName, lastName, grade));
    }
}
