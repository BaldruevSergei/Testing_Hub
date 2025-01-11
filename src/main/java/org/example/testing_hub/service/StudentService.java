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
    private static final Set<String> GENERATED_LOGINS = new HashSet<>();

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Обработка Excel-файла и сохранение студентов.
     */
    @Transactional
    public List<StudentDTO> processAndSaveStudents(InputStream inputStream) {
        logger.info("Начата обработка Excel-файла...");

        List<StudentDTO> studentDTOs = parseExcel(inputStream);

        if (studentDTOs.isEmpty()) {
            logger.warn("Файл не содержит данных студентов.");
            return Collections.emptyList();
        }

        String grade = studentDTOs.get(0).getGrade();
        ClassEntity classEntity = classService.saveClass(grade);

        studentDTOs.forEach(dto -> {
            Student student = convertToStudent(dto, classEntity);
            if (!existsStudentByLogin(student.getLogin())) {
                studentRepository.save(student);
                logger.info("Студент {} {} успешно сохранен.", student.getFirstName(), student.getLastName());
            } else {
                logger.warn("Студент с логином {} уже существует. Пропуск сохранения.", student.getLogin());
            }
        });

        logger.info("Обработка Excel-файла завершена.");
        return studentDTOs;
    }

    /**
     * Методы для работы с репозиторием.
     */
    public boolean existsStudentByLogin(String login) {
        return studentRepository.existsByLogin(login);
    }

    public List<Student> findStudentsByClassName(String className) {
        return studentRepository.findByClassName(className);
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

    public List<Student> findStudentsByFullNameAndClass(String firstName, String lastName, String className) {
        return studentRepository.findByFullNameAndClass(firstName, lastName, className);
    }



    /**
     * Парсинг Excel-файла в список DTO студентов.
     */
    private List<StudentDTO> parseExcel(InputStream inputStream) {
        List<StudentDTO> students = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            String grade = extractGrade(sheet);
            logger.info("Извлечен класс: {}", grade);

            for (Row row : sheet) {
                if (row.getRowNum() < 3) continue; // Пропуск первых строк

                StudentDTO studentDTO = parseRow(row, students.size() + 1, grade);
                if (studentDTO != null) {
                    students.add(studentDTO);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка обработки файла Excel: " + e.getMessage(), e);
        }

        return students;
    }

    /**
     * Извлечение названия класса из Excel.
     */
    private String extractGrade(Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        Cell gradeCell = headerRow != null ? headerRow.getCell(2) : null;
        return gradeCell != null ? gradeCell.getStringCellValue().split(":")[0].trim() : "Неизвестно";
    }

    /**
     * Парсинг одной строки Excel.
     */
    private StudentDTO parseRow(Row row, int studentNumber, String grade) {
        Cell nameCell = row.getCell(1);
        if (nameCell == null || nameCell.getStringCellValue().trim().isEmpty()) {
            return null;
        }

        String[] nameParts = nameCell.getStringCellValue().split(" ", 2);
        String firstName = nameParts.length > 0 ? nameParts[0].trim() : "Неизвестно";
        String lastName = nameParts.length > 1 ? nameParts[1].trim() : "Неизвестно";

        return new StudentDTO(studentNumber, firstName, lastName, grade, generateUniqueLogin(grade), generateRandomPassword());
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
    private String generateUniqueLogin(String grade) {
        String login;
        do {
            login = "User" + grade.replaceAll("\\D", "") + String.format("%04d", RANDOM.nextInt(10000));
        } while (!GENERATED_LOGINS.add(login));
        return login;
    }

    /**
     * Генерация случайного пароля для студента.
     */
    private String generateRandomPassword() {
        return String.format("%06d", RANDOM.nextInt(1000000));
    }

    /**
     * Загрузка и обработка файла по URL.
     */
    public List<StudentDTO> loadStudentsFromUrl(String fileUrl) {
        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            return processAndSaveStudents(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла с URL: " + fileUrl, e);
        }
    }
    @Transactional
    public void saveStudentsWithClass(List<Student> students, String grade) {
        // Сохранение класса
        ClassEntity classEntity = classService.saveClass(grade);

        // Сохранение студентов
        students.forEach(student -> {
            student.setClassEntity(classEntity);
            if (!studentRepository.existsByLogin(student.getLogin())) {
                studentRepository.save(student);
                logger.info("Студент {} {} успешно сохранен.", student.getFirstName(), student.getLastName());
            } else {
                logger.warn("Студент с логином {} уже существует. Пропуск сохранения.", student.getLogin());
            }
        });
    }

}
