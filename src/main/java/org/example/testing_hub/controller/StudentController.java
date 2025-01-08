package org.example.testing_hub.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.testing_hub.dto.StudentDTO;
import org.example.testing_hub.service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Загрузка списка студентов из локального Excel файла")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список студентов загружен успешно",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка загрузки файла", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public List<StudentDTO> uploadStudents(@RequestParam("file") MultipartFile file) {
        try {
            // Передаем InputStream файла в метод parseExcel
            return studentService.parseExcel(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка обработки файла!");
        }
    }

    @Operation(summary = "Загрузка списка студентов из внешнего источника (по URL)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список студентов загружен успешно",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка загрузки из URL", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @GetMapping("/load")
    public List<StudentDTO> loadStudentsFromUrl(
            @RequestParam("url") @RequestBody(description = "Ссылка на файл Excel") String fileUrl) {
        try {
            return studentService.loadStudentsFromUrl(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка загрузки студентов из внешнего источника!");
        }
    }
}
