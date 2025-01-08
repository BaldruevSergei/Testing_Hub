package org.example.testing_hub.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.testing_hub.dto.StudentDTO;
import org.example.testing_hub.entity.ClassEntity;
import org.example.testing_hub.entity.Role;
import org.example.testing_hub.entity.User;
import org.example.testing_hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.InputStream;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Set<String> generatedLogins = new HashSet<>();

    @Autowired
    private ClassService classService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Метод для обработки Excel-файла и сохранения студентов с классом в базе данных.
     *
     * @param inputStream  входной поток Excel-файла
     * @param defaultGrade класс, указанный по умолчанию
     */
    public List<StudentDTO> processAndSaveStudents(InputStream inputStream, String defaultGrade) {
        // Парсинг Excel-файла в DTO
        List<StudentDTO> studentDTOs = parseExcel(inputStream, defaultGrade);

        // Сохранение или получение класса
        ClassEntity classEntity = classService.saveClass(defaultGrade); // Создаем/получаем ClassEntity

        // Преобразование DTO в сущности User
        List<User> students = studentDTOs.stream()
                .map(dto -> convertToUser(dto, classEntity)) // Используем classEntity
                .collect(Collectors.toList());

        // Сохранение студентов с привязкой к классу
        saveStudentsWithClass(students, defaultGrade);

        // Возвращение списка DTO
        return studentDTOs;
    }




    /**
     * Сохранение списка студентов с привязкой к классу.
     *
     * @param students список студентов
     * @param grade    класс
     */
    public void saveStudentsWithClass(List<User> students, String grade) {
        // Сохранение или получение существующего класса
        ClassEntity classEntity = classService.saveClass(grade);

        // Привязка класса к каждому студенту и проверка перед сохранением
        students.forEach(student -> {
            student.setClassEntity(classEntity);
            // Проверка: существует ли пользователь с таким username
            if (!userRepository.existsByUsername(student.getUsername())) {
                userRepository.save(student); // Сохранение студента в базу
            } else {
                System.out.println("Пользователь с логином " + student.getUsername() + " уже существует!");
            }
        });

    }


    /**
     * Парсинг Excel-файла в список DTO студентов.
     *
     * @param inputStream  входной поток Excel-файла
     * @param defaultGrade класс по умолчанию
     * @return список DTO студентов
     */
    public List<StudentDTO> parseExcel(InputStream inputStream, String defaultGrade) {
        List<StudentDTO> students = new ArrayList<>();
        int studentNumber = 1;

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Пропускаем заголовок

                Cell fullNameCell = row.getCell(1);
                if (fullNameCell == null || fullNameCell.getStringCellValue().trim().isEmpty()) {
                    continue; // Пропускаем строки с пустыми именами
                }

                String[] nameParts = fullNameCell.getStringCellValue().split(" ", 2);
                String name = nameParts.length > 0 ? nameParts[0].trim() : "Неизвестно";
                String surname = nameParts.length > 1 ? nameParts[1].trim() : "Неизвестно";

                String login = generateUniqueLogin(defaultGrade.replaceAll("\\D", ""));
                String password = generateRandomPassword();

                students.add(new StudentDTO(studentNumber++, name, surname, defaultGrade, login, password));
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обработки файла Excel: " + e.getMessage(), e);
        }

        return students;
    }


    /**
     * Генерация уникального логина для студента.
     *
     * @param gradeNumber номер класса
     * @return уникальный логин
     */
    private String generateUniqueLogin(String gradeNumber) {
        String login;
        do {
            login = "User" + gradeNumber + String.format("%04d", RANDOM.nextInt(10000));
        } while (!generatedLogins.add(login));

        return login;
    }

    /**
     * Генерация случайного пароля для студента.
     *
     * @return случайный пароль
     */
    private String generateRandomPassword() {
        return String.format("%06d", RANDOM.nextInt(1000000));
    }

    /**
     * Получение значения из ячейки Excel.
     *
     * @param cell ячейка Excel
     * @return значение в виде строки
     */
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
    public User convertToUser(StudentDTO dto, ClassEntity classEntity) {
        User user = new User();
        user.setFirstName(dto.getName()); // Соответствие "name" → "firstName"
        user.setLastName(dto.getSurname()); // Соответствие "surname" → "lastName"
        user.setUsername(dto.getLogin()); // Соответствие "login" → "username"
        user.setPassword(dto.getPassword()); // Соответствие "password" → "password"
        user.setRole(Role.STUDENT); // Установка роли по умолчанию
        user.setClassEntity(classEntity); // Привязка к классу
        return user;
    }



}
