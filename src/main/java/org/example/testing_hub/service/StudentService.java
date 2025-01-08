package org.example.testing_hub.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.testing_hub.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.*;

@Service
public class StudentService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Set<String> generatedLogins = new HashSet<>();

    public List<StudentDTO> parseExcel(InputStream inputStream, String defaultGrade) {
        List<StudentDTO> students = new ArrayList<>();
        int studentNumber = 1;

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Пропускаем заголовок

                Cell fullNameCell = row.getCell(1); // Предполагаем, что имя и фамилия в первой ячейке

                if (fullNameCell == null) continue; // Пропускаем строки с пустыми данными

                // Читаем значение из ячейки
                String fullName = getCellValueAsString(fullNameCell).trim();

                if (fullName.isEmpty()) continue; // Пропускаем строки с пустыми именами

                // Разделяем имя и фамилию
                String[] nameParts = fullName.split(" ", 2);
                String name = nameParts.length > 0 ? nameParts[0].trim() : "Неизвестно";
                String surname = nameParts.length > 1 ? nameParts[1].trim() : "Неизвестно";

                // Генерация логина и пароля
                String login = generateUniqueLogin(defaultGrade.replaceAll("\\D", ""));
                String password = generateRandomPassword();

                // Создаем объект StudentDTO
                StudentDTO student = new StudentDTO(studentNumber++, name, surname, defaultGrade, login, password);
                students.add(student);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обработки файла Excel", e);
        }

        return students;
    }





    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return DateUtil.isCellDateFormatted(cell)
                        ? cell.getDateCellValue().toString()
                        : String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private String generateUniqueLogin(String gradeNumber) {
        String login;
        do {
            login = "User" + gradeNumber + String.format("%04d", RANDOM.nextInt(10000));
        } while (!generatedLogins.add(login));

        return login;
    }

    private String generateRandomPassword() {
        return String.format("%06d", RANDOM.nextInt(1000000));
    }






    public List<StudentDTO> loadStudentsFromUrl(String fileUrl, String defaultGrade) {
        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            return parseExcel(inputStream, defaultGrade);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки студентов из URL", e);
        }
    }
}
