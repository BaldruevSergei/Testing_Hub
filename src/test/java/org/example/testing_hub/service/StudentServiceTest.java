package org.example.testing_hub.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.testing_hub.dto.StudentDTO;
import org.example.testing_hub.entity.Student;
import org.example.testing_hub.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassService classService;

    @InjectMocks
    private StudentService studentService;

    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockWebServer = new MockWebServer();
    }

    @AfterEach
    void tearDown() {
        try {
            mockWebServer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void loadStudentsFromUrl_ShouldParseCorrectly() throws Exception {
        // Создаем Excel-файл в памяти
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");

            // Заполняем заголовок
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(2).setCellValue("8А: Информатика (Бальдруев С. В.) - II четверть");

            // Добавляем данные студентов
            Row student1Row = sheet.createRow(3);
            student1Row.createCell(1).setCellValue("Иван Петров");

            Row student2Row = sheet.createRow(4);
            student2Row.createCell(1).setCellValue("Анна Сидорова");

            workbook.write(outStream);
        }

        byte[] excelContent = outStream.toByteArray();

        // Устанавливаем MockWebServer для возврата файла
        mockWebServer.enqueue(new MockResponse()
                .setBody(new Buffer().write(excelContent))
                .addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        mockWebServer.start();

        // Вызываем метод для загрузки
        InputStream inputStream = mockWebServer.url("/test.xlsx").uri().toURL().openStream();
        List<StudentDTO> students = studentService.parseExcel(inputStream);

        // Проверяем результат
        assertNotNull(students, "Список студентов не должен быть null");
        assertEquals(2, students.size(), "Ожидаем 2 студента");

        // Проверяем данные первого студента
        StudentDTO student1 = students.get(0);
        assertEquals("Иван", student1.getFirstName());
        assertEquals("Петров", student1.getLastName());
        assertEquals("8А", student1.getGrade()); // Класс извлекается из заголовка

        // Проверяем данные второго студента
        StudentDTO student2 = students.get(1);
        assertEquals("Анна", student2.getFirstName());
        assertEquals("Сидорова", student2.getLastName());
        assertEquals("8А", student2.getGrade()); // Класс извлекается из заголовка
    }
}