package org.example.testing_hub.dto;

public class StudentDTO {
    private int studentNumber;
    private String name;      // Имя
    private String surname;   // Фамилия
    private String grade;     // Класс (например: "8А")
    private String login;     // Логин (например: "User8001")
    private String password;  // Пароль (например: "875342")

    // Конструктор с параметрами
    public StudentDTO(int studentNumber,String name, String surname, String grade, String login, String password) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.surname = surname;
        this.grade = grade;
        this.login = login;
        this.password = password;
    }

    // Геттеры и сеттеры


    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "studentNumber='" + studentNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", grade='" + grade + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
