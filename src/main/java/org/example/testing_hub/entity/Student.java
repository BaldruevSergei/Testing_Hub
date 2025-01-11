package org.example.testing_hub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "students")
public class Student extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    @JsonIgnore
    private ClassEntity classEntity;

    @Column(nullable = false)
    private String grade; // Класс ученика (например, "9A")

    @Column(insertable = false, updatable = false)
    private String email;

    @PrePersist
    @PreUpdate
    private void prepareStudentEntity() {
        // Удаляем email для Student
        if (getEmail() != null) {
            setEmail(null);
        }
        // Устанавливаем роль, если она еще не установлена
        if (getRole() == null) {
            setRole(Role.STUDENT);
        }
    }
}
