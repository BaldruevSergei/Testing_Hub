package org.example.testing_hub.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.testing_hub.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.SecureRandom;
import java.util.*;

@Service
public class ExcelService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Set<String> generatedLogins = new HashSet<>(); // Для хранения уникальных логинов

    /**
     * Парсит Excel-файл и преобразует его строки в список объектов StudentDTO.
     *
     * @param inputStream поток данных Excel-файла.
     * @return Список объектов StudentDTO.
     */
    public List<StudentDTO> parseExcel(InputStream inputStream) {
        List<StudentDTO> students = new ArrayList<>();
        int studentNumber = 1; // Начальный номер для учеников

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Читаем первый лист Excel

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Пропускаем строку с заголовком
                }

                Cell fullNameCell = row.getCell(0); // Ячейка с полным именем
                Cell gradeCell = row.getCell(1);   // Ячейка с классом

                if (fullNameCell == null || gradeCell == null) {
                    continue; // Пропускаем строки с отсутствующими данными
                }

                // Получаем значения ячеек как строки
                String fullName = getCellValueAsString(fullNameCell).trim();
                String grade = getCellValueAsString(gradeCell).trim();

                // Разделяем полное имя на имя и фамилию
                String[] nameParts = fullName.split(" ", 2);
                String name = nameParts.length > 0 ? nameParts[0] : "Неизвестно";
                String surname = nameParts.length > 1 ? nameParts[1] : "Неизвестно";

                // Генерируем логин
                String login = generateUniqueLogin(grade);

                // Генерируем пароль
                String password = generateRandomPassword();

                // Создаем объект StudentDTO
                StudentDTO student = new StudentDTO(
                        studentNumber,
                        name,
                        surname,
                        grade,
                        login,
                        password
                );

                students.add(student);
                studentNumber++; // Увеличиваем номер для следующего ученика
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обработки файла Excel", e);
        }

        return students;
    }

    /**
     * Извлекает значение ячейки как строку.
     *
     * @param cell объект ячейки.
     * @return Строковое представление значения ячейки.
     */
    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return DateUtil.isCellDateFormatted(cell)
                        ? cell.getDateCellValue().toString() // Если это дата, возвращаем дату как строку
                        : String.valueOf((int) cell.getNumericCellValue()); // Если число, преобразуем его в строку
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula(); // Возвращаем формулу как строку
            default:
                return ""; // Если ячейка пустая или другой тип, возвращаем пустую строку
        }
    }

    /**
     * Генерирует уникальный логин на основе класса ученика.
     *
     * @param grade Класс ученика.
     * @return Уникальный логин.
     */
    private String generateUniqueLogin(String grade) {
        // Извлекаем числовую часть из класса
        String gradeNumber = grade.replaceAll("\\D", ""); // Удаляем все, кроме цифр
        gradeNumber = gradeNumber.isEmpty() ? "00" : gradeNumber; // Если цифр нет, используем "00"

        // Генерируем логин в формате User<GradeNumber><4 цифры>
        String login;
        do {
            login = "User" + gradeNumber + String.format("%04d", RANDOM.nextInt(10000));
        } while (!generatedLogins.add(login)); // Проверяем уникальность логина

        return login;
    }

    /**
     * Генерирует случайный пароль для ученика.
     *
     * @return Случайный 6-значный пароль.
     */
    private String generateRandomPassword() {
        return String.format("%06d", RANDOM.nextInt(1000000)); // Генерируем 6-значный пароль
    }
}
