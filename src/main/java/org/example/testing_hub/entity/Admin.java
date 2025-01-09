package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admins")
public class Admin extends User {

    @Column(nullable = false)
    private String fullName; // Полное имя администратора

    private String department;
    public Admin() {
        this.setRole(Role.ADMIN); // Устанавливаем роль по умолчанию
    }
}
