package org.example.testing_hub.service;

import org.example.testing_hub.entity.FileEntity;
import org.example.testing_hub.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public FileEntity getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    public FileEntity createFile(FileEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
}
