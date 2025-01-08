package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "system_settings")
public class SystemSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String key; // Ключ настройки (например, "DEFAULT_LANGUAGE")

    @Column(nullable = false)
    private String value; // Значение настройки (например, "ru")

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValueType valueType; // Тип значения (например, STRING, INTEGER, BOOLEAN)

    @Column
    private String description; // Описание настройки (например, "Язык по умолчанию")
}
