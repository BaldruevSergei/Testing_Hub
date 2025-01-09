package org.example.testing_hub.service;

import jakarta.validation.Valid;
import org.example.testing_hub.dto.UserUpdateDTO;
import org.example.testing_hub.entity.Student;
import org.example.testing_hub.entity.Teacher;
import org.example.testing_hub.entity.Admin;
import org.example.testing_hub.entity.User;
import org.example.testing_hub.repository.StudentRepository;
import org.example.testing_hub.repository.TeacherRepository;
import org.example.testing_hub.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AdminRepository adminRepository;

    public User findUserByLogin(String login) {
        Optional<? extends User> user = Optional.empty();

        user = studentRepository.findByLogin(login);
        if (user.isEmpty()) {
            user = teacherRepository.findByLogin(login);
        }
        if (user.isEmpty()) {
            user = adminRepository.findByLogin(login);
        }

        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean existsByLogin(String login) {
        return studentRepository.existsByLogin(login) ||
                teacherRepository.existsByLogin(login) ||
                adminRepository.existsByLogin(login);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<? extends User> user = Optional.empty();

        user = studentRepository.findById(id);
        if (user.isEmpty()) {
            user = teacherRepository.findById(id);
        }
        if (user.isEmpty()) {
            user = adminRepository.findById(id);
        }

        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        if (user instanceof Student) {
            return studentRepository.save((Student) user);
        } else if (user instanceof Teacher) {
            return teacherRepository.save((Teacher) user);
        } else if (user instanceof Admin) {
            return adminRepository.save((Admin) user);
        } else {
            throw new RuntimeException("Unsupported user type");
        }
    }

    public User updateUser(Long id, @Valid UserUpdateDTO updateUserDto) {
        User user = getUserById(id);

        if (updateUserDto.getFirstName() != null) {
            user.setFirstName(updateUserDto.getFirstName());
        }
        if (updateUserDto.getLastName() != null) {
            user.setLastName(updateUserDto.getLastName());
        }
        if (updateUserDto.getEmail() != null && user instanceof Teacher) {
            ((Teacher) user).setEmail(updateUserDto.getEmail());
        }

        if (user instanceof Student) {
            return studentRepository.save((Student) user);
        } else if (user instanceof Teacher) {
            return teacherRepository.save((Teacher) user);
        } else if (user instanceof Admin) {
            return adminRepository.save((Admin) user);
        } else {
            throw new RuntimeException("Unsupported user type");
        }
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);

        if (user instanceof Student) {
            studentRepository.delete((Student) user);
        } else if (user instanceof Teacher) {
            teacherRepository.delete((Teacher) user);
        } else if (user instanceof Admin) {
            adminRepository.delete((Admin) user);
        } else {
            throw new RuntimeException("Unsupported user type");
        }
    }
}
