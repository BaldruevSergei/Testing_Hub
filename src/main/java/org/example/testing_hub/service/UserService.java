package org.example.testing_hub.service;
import org.example.testing_hub.dto.UserUpdateDTO;
import org.example.testing_hub.entity.ClassEntity;
import org.example.testing_hub.entity.User;
import org.example.testing_hub.repository.ClassRepository;
import org.example.testing_hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<User> getStudentsByClass(Long classId) {
        return userRepository.findByClassEntity_Id(classId);
    }

    public User updateUser(Long id, UserUpdateDTO updatedUserDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        existingUser.setFirstName(updatedUserDto.getFirstName());
        existingUser.setLastName(updatedUserDto.getLastName());
        existingUser.setEmail(updatedUserDto.getEmail());

        return userRepository.save(existingUser);
    }

    // Метод для сохранения студентов
    public void saveStudents(List<User> students, String grade) {
        // Проверить, существует ли класс
        ClassEntity classEntity = classRepository.findByGrade(grade)
                .orElseGet(() -> {
                    ClassEntity newClass = new ClassEntity();
                    newClass.setGrade(grade);
                    return classRepository.save(newClass);
                });

        // Привязать класс к каждому студенту и сохранить в базе
        students.forEach(student -> {
            student.setClassEntity(classEntity);
            userRepository.save(student);
        });
    }
}
