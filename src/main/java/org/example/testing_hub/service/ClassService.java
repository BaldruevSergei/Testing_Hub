package org.example.testing_hub.service;

import org.example.testing_hub.entity.ClassEntity;
import org.example.testing_hub.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public List<ClassEntity> getAllClasses() {
        return classRepository.findAll();
    }

    public ClassEntity getClassById(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
    }

    public ClassEntity createClass(ClassEntity classEntity) {
        return classRepository.save(classEntity);
    }

    public ClassEntity updateClass(Long id, ClassEntity updatedClass) {
        ClassEntity existingClass = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));

        // Обновляем поля
        existingClass.setName(updatedClass.getName());
        existingClass.setDescription(updatedClass.getDescription());
        existingClass.setStudents(updatedClass.getStudents());

        return classRepository.save(existingClass);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }
}
