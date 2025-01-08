package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "classes")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String grade; // Название класса (например, "9А")

    @Column
    private String description; // Поле description для описания класса

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
    private List<User> students; // Список учеников, связанных с классом
}
