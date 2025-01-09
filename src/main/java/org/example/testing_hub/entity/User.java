package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login; // Логин пользователя

    @Column(nullable = false)
    private String password; // Пароль пользователя

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // Роль пользователя (ADMIN, TEACHER, STUDENT)

    @Column(nullable = false)
    private String firstName; // Имя пользователя

    @Column(nullable = false)
    private String lastName; // Фамилия пользователя
}
