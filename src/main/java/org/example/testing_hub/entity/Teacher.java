package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teachers")
public class Teacher extends User {

    @Column(nullable = false, unique = true)
    private String email; // Email для связи

    @Column(nullable = false)
    private String firstName; // Имя

    @Column(nullable = false)
    private String lastName; // Фамилия

    public Teacher() {
        this.setRole(Role.TEACHER); // Устанавливаем роль по умолчанию
    }
}
