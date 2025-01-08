package org.example.testing_hub.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.testing_hub.dto.StudentDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService studentService;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
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
    void loadStudentsFromUrl_ShouldParseCorrectly() {
        try {
            // Создаем Excel-файл в памяти
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Students");

                // Создаем заголовок
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Имя Фамилия");
                headerRow.createCell(1).setCellValue("Класс");

                // Добавляем данные студентов
                Row student1Row = sheet.createRow(1);
                student1Row.createCell(0).setCellValue("Иван Петров");
                student1Row.createCell(1).setCellValue("10А");

                Row student2Row = sheet.createRow(2);
                student2Row.createCell(0).setCellValue("Анна Сидорова");
                student2Row.createCell(1).setCellValue("9Б");

                workbook.write(outStream);
            }

            byte[] excelContent = outStream.toByteArray();

            // Устанавливаем MockWebServer для возврата файла
            mockWebServer.enqueue(new MockResponse()
                    .setBody(new Buffer().write(excelContent))
                    .addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

            mockWebServer.start();



            // Вызываем метод для загрузки
            String fileUrl = mockWebServer.url("/test.xlsx").toString();
            String defaultGrade = "10А"; // Пример значения для второго параметра
            List<StudentDTO> students = studentService.loadStudentsFromUrl(fileUrl, defaultGrade);


            // Проверяем студентов
            assertNotNull(students, "Список студентов не должен быть null");
            assertEquals(2, students.size(), "Ожидаем 2 студента");

            // Проверяем данные первого студента
            StudentDTO student1 = students.get(0);
            assertEquals("Иван", student1.getName());
            assertEquals("Петров", student1.getSurname());
            assertEquals("10А", student1.getGrade());

            // Проверяем данные второго студента
            StudentDTO student2 = students.get(1);
            assertEquals("Анна", student2.getName());
            assertEquals("Сидорова", student2.getSurname());
            assertEquals("9Б", student2.getGrade());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Не удалось загрузить студентов из URL: " + e.getMessage());
        }
    }
}
