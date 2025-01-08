package org.example.testing_hub.controller;

import org.example.testing_hub.entity.FileEntity;
import org.example.testing_hub.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public List<FileEntity> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/{id}")
    public FileEntity getFileById(@PathVariable Long id) {
        return fileService.getFileById(id);
    }

    @PostMapping
    public FileEntity createFile(@RequestBody FileEntity fileEntity) {
        return fileService.createFile(fileEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
    }
}
