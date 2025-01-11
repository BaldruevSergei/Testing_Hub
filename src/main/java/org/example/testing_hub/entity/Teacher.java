package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "teachers")
public class Teacher extends User {

    @Column(nullable = false)
    private String subject; // Предмет, который преподаёт учитель

    @Column(nullable = false)
    private String position; // Должность, например, "Классный руководитель"

    @PrePersist
    @PreUpdate
    private void validateTeacherEntity() {
        if (getEmail() == null || getEmail().isEmpty()) {
            throw new IllegalStateException("Email is required for Teacher");
        }
        if (getRole() == null) {
            setRole(Role.TEACHER); // Устанавливаем роль по умолчанию
        }
    }
}
