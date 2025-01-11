package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admins")
public class Admin extends User {

    @Column
    private String roleDescription; // Описание роли (например, "Суперадминистратор")

    @Column(nullable = false)
    private String department;

    // Валидация email
    @PrePersist
    @PreUpdate
    private void validateAndSetDefaults() {
        // Проверка email
        if (getEmail() == null || getEmail().isEmpty()) {
            throw new IllegalStateException("Email is required for Admin");
        }

        // Установка роли по умолчанию
        if (getRole() == null) { // Устанавливаем роль только если она еще не установлена
            setRole(Role.ADMIN);
        }
    }

}
