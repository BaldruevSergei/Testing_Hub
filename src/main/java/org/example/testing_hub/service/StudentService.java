package org.example.testing_hub.service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.testing_hub.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    public List<StudentDTO> loadStudentsFromUrl(String fileUrl) {
        List<StudentDTO> students = new ArrayList<>();

        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Пропускаем заголовок
                    continue;
                }

                Cell nameCell = row.getCell(0);
                Cell surnameCell = row.getCell(1);
                Cell gradeCell = row.getCell(2);

                String name = nameCell != null ? nameCell.getStringCellValue().trim() : "";
                String surname = surnameCell != null ? surnameCell.getStringCellValue().trim() : "";
                String grade = gradeCell != null ? gradeCell.getStringCellValue().trim() : "";

                // Генерация логина и пароля
                String login = generateUniqueLogin(grade);
                String password = generateRandomPassword();

                // Создаем объект StudentDTO и добавляем в список
                StudentDTO student = new StudentDTO(name, surname, grade, login, password);
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка загрузки файла с URL: " + fileUrl);
        }

        return students;
    }

    public List<StudentDTO> parseExcel(InputStream inputStream) {
        List<StudentDTO> students = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Пропускаем заголовок
                    continue;
                }

                Cell nameCell = row.getCell(0);
                Cell surnameCell = row.getCell(1);
                Cell gradeCell = row.getCell(2);

                String name = nameCell != null ? nameCell.getStringCellValue().trim() : "";
                String surname = surnameCell != null ? surnameCell.getStringCellValue().trim() : "";
                String grade = gradeCell != null ? gradeCell.getStringCellValue().trim() : "";

                // Генерация логина и пароля
                String login = generateUniqueLogin(grade);
                String password = generateRandomPassword();

                // Создаем объект StudentDTO и добавляем в список
                StudentDTO student = new StudentDTO(name, surname, grade, login, password);
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка обработки файла");
        }

        return students;
    }

    // Генерация уникального логина на основе класса
    private String generateUniqueLogin(String grade) {
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // 4 случайных цифры
        return grade + randomPart;
    }

    // Генерация случайного пароля
    private String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 6; i++) { // 6 случайных цифр
            password.append((int) (Math.random() * 10));
        }
        return password.toString();
    }
}
