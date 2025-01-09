package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "students")
public class Student extends User {

    @Column(nullable = false)
    private String firstName; // Имя

    @Column(nullable = false)
    private String lastName; // Фамилия

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity; // Связь с классом

    public Student() {
        this.setRole(Role.STUDENT); // Устанавливаем роль по умолчанию
    }
}
