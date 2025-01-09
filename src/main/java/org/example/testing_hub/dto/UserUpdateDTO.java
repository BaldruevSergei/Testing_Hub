package org.example.testing_hub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateDTO {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email; // Только для Teacher

    private String className; // Учебный класс (только для Student)

    private String department; // Отдел или департамент (только для Admin)

    @NotBlank(message = "Login cannot be blank")
    private String login; // Логин для всех пользователей

    // Геттеры и сеттеры


    public @NotBlank(message = "First name cannot be blank") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name cannot be blank") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name cannot be blank") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name cannot be blank") String lastName) {
        this.lastName = lastName;
    }

    public @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public @NotBlank(message = "Login cannot be blank") String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank(message = "Login cannot be blank") String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "UserUpdateDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", className='" + className + '\'' +
                ", department='" + department + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
