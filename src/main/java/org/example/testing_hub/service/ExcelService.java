package org.example.testing_hub.service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.testing_hub.dto.StudentDTO;

import java.io.InputStream;
import java.security.SecureRandom;
import java.util.*;

public class ExcelService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Set<String> generatedLogins = new HashSet<>(); // Для хранения уникальных логинов

    public List<StudentDTO> parseExcel(InputStream inputStream) {
        List<StudentDTO> students = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Пропускаем заголовок
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

                // Создаем объект StudentDTO
                StudentDTO student = new StudentDTO(name, surname, grade, login, password);
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    private String generateUniqueLogin(String grade) {
        String login;
        do {
            login = grade + String.format("%04d", RANDOM.nextInt(10000));
        } while (!generatedLogins.add(login)); // Проверяем уникальность логина
        return login;
    }

    private String generateRandomPassword() {
        return String.format("%06d", RANDOM.nextInt(1000000)); // Генерация 6-значного пароля
    }
}
