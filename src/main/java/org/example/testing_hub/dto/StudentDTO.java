package org.example.testing_hub.dto;

public class StudentDTO {
    private String name;
    private String surname;
    private String grade;
    private String login; // Логин ученика
    private String password; // Пароль ученика

    // Конструктор без параметров (для сериализации)
    public StudentDTO() {
    }

    // Конструктор с параметрами
    public StudentDTO(String name, String surname, String grade, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.grade = grade;
        this.login = login;
        this.password = password;
    }

    // Геттеры и сеттеры
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

    // Метод toString
    @Override
    public String toString() {
        return "StudentDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", grade='" + grade + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
