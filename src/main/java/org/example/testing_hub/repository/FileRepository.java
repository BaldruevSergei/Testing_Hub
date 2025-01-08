package org.example.testing_hub.repository;

import org.example.testing_hub.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByUploadedBy_Id(Long userId); // Поиск файлов по пользователю
}
