package org.example.testing_hub.controller;
import org.example.testing_hub.dto.UserUpdateDTO;
import org.example.testing_hub.entity.Admin;
import org.example.testing_hub.entity.Student;
import org.example.testing_hub.entity.Teacher;
import org.example.testing_hub.entity.User;
import org.example.testing_hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Получить всех пользователей (студенты, учителя, администраторы)
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Получить пользователя по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}