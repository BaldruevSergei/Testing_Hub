package org.example.testing_hub.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ExcelTestFileGenerator {

    public static InputStream createTestExcelFile() {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            // Создаем новый лист
            Sheet sheet = workbook.createSheet("Students");

            // Добавляем заголовок
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Имя Фамилия");
            headerRow.createCell(1).setCellValue("Класс");

            // Добавляем тестовые данные студентов
            Row student1Row = sheet.createRow(1);
            student1Row.createCell(0).setCellValue("Иван Петров");
            student1Row.createCell(1).setCellValue("10А");

            Row student2Row = sheet.createRow(2);
            student2Row.createCell(0).setCellValue("Анна Сидорова");
            student2Row.createCell(1).setCellValue("9Б");

            // Записываем в поток
            workbook.write(outStream);

            // Преобразуем поток в InputStream
            return new ByteArrayInputStream(outStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании тестового Excel-файла", e);
        }
    }
}
