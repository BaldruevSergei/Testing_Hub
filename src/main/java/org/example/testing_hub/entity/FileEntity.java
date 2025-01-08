package org.example.testing_hub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName; // Имя файла

    @Column(nullable = false)
    private String filePath; // Путь к файлу на сервере

    @Column(nullable = false)
    private LocalDateTime uploadedAt; // Дата загрузки файла

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy; // Кто загрузил файл
}
