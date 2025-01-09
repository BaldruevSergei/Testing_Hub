package org.example.testing_hub.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.testing_hub.dto.StudentDTO;
import org.example.testing_hub.entity.ClassEntity;
import org.example.testing_hub.entity.Student;
import org.example.testing_hub.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.*;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Set<String> generatedLogins = new HashSet<>();

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Обработка Excel-файла и сохранение студентов.
     */
    @Transactional
    public List<StudentDTO> processAndSaveStudents(InputStream inputStream, String defaultGrade) {
        logger.info("Начата обработка Excel-файла для класса {}", defaultGrade);
        List<StudentDTO> studentDTOs = parseExcel(inputStream, defaultGrade);

        ClassEntity classEntity = classService.saveClass(defaultGrade);

        studentDTOs.forEach(dto -> {
            Student student = convertToStudent(dto, classEntity);
            if (!studentRepository.existsByLogin(student.getLogin())) {
                studentRepository.save(student);
                logger.info("Студент {} {} успешно сохранен.", student.getFirstName(), student.getLastName());
            } else {
                logger.warn("Студент с логином {} уже существует.", student.getLogin());
            }
        });

        return studentDTOs;
    }

    /**
     * Сохранение списка студентов с привязкой к классу.
     */
    @Transactional
    public void saveStudentsWithClass(List<Student> students, String grade) {
        ClassEntity classEntity = classService.saveClass(grade);

        students.forEach(student -> {
            student.setClassEntity(classEntity);
            if (!studentRepository.existsByLogin(student.getLogin())) {
                studentRepository.save(student);
                logger.info("Студент {} {} успешно сохранен.", student.getFirstName(), student.getLastName());
            } else {
                logger.warn("Студент с логином {} уже существует.", student.getLogin());
            }
        });
    }

    /**
     * Методы из репозитория.
     */
    public Optional<Student> findStudentByLogin(String login) {
        return studentRepository.findByLogin(login);
    }

    public boolean existsStudentByLogin(String login) {
        return studentRepository.existsByLogin(login);
    }

    public List<Student> findStudentsByFirstName(String firstName) {
        return studentRepository.findByFirstName(firstName);
    }

    public List<Student> findStudentsByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    public List<Student> findStudentsByFullName(String firstName, String lastName) {
        return studentRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<Student> findStudentsByClass(String grade) {
        return studentRepository.findByClassEntity_Grade(grade);
    }

    public List<Student> findStudentsByFullNameAndClass(String firstName, String lastName, String grade) {
        return studentRepository.findByFirstNameAndLastNameAndClassEntity_Grade(firstName, lastName, grade);
    }

    /**
     * Парсинг Excel-файла в список DTO студентов.
     */
    public List<StudentDTO> parseExcel(InputStream inputStream, String defaultGrade) {
        List<StudentDTO> students = new ArrayList<>();
        int studentNumber = 1;

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                // Пропускаем первую строку с заголовками
                if (row.getRowNum() == 0) continue;

                // Получаем ячейки
                Cell nameCell = row.getCell(0);
                Cell gradeCell = row.getCell(1);

                // Пропускаем строки с пустыми значениями
                if (nameCell == null || nameCell.getStringCellValue().trim().isEmpty()) {
                    continue;
                }

                String[] nameParts = nameCell.getStringCellValue().split(" ", 2);
                String firstName = nameParts.length > 0 ? nameParts[0].trim() : "Неизвестно";
                String lastName = nameParts.length > 1 ? nameParts[1].trim() : "Неизвестно";

                String grade = gradeCell != null ? gradeCell.getStringCellValue().trim() : defaultGrade;

                students.add(new StudentDTO(studentNumber++, firstName, lastName, grade, generateUniqueLogin(grade), generateRandomPassword()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обработки файла Excel: " + e.getMessage(), e);
        }

        return students;
    }


    /**
     * Парсинг одной строки Excel.
     */
    private StudentDTO parseRow(Row row, int studentNumber, String defaultGrade) {
        Cell fullNameCell = row.getCell(1);
        if (fullNameCell == null || fullNameCell.getStringCellValue().trim().isEmpty()) {
            return null;
        }

        String[] nameParts = fullNameCell.getStringCellValue().split(" ", 2);
        String firstName = nameParts.length > 0 ? nameParts[0].trim() : "Неизвестно";
        String lastName = nameParts.length > 1 ? nameParts[1].trim() : "Неизвестно";

        String login = generateUniqueLogin(defaultGrade.replaceAll("\\D", ""));
        String password = generateRandomPassword();

        return new StudentDTO(studentNumber, firstName, lastName, defaultGrade, login, password);
    }

    /**
     * Преобразование DTO в сущность Student.
     */
    private Student convertToStudent(StudentDTO dto, ClassEntity classEntity) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setLogin(dto.getLogin());
        student.setPassword(dto.getPassword());
        student.setClassEntity(classEntity);
        return student;
    }

    /**
     * Генерация уникального логина для студента.
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
     */
    private String generateRandomPassword() {
        return String.format("%06d", RANDOM.nextInt(1000000));
    }

    public List<StudentDTO> loadStudentsFromUrl(String fileUrl, String defaultGrade) {
        try {
            URL url = new URL(fileUrl);
            try (InputStream inputStream = url.openStream()) {
                return processAndSaveStudents(inputStream, defaultGrade);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла с URL: " + fileUrl, e);
        }
    }

}
