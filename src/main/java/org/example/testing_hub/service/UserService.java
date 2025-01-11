package org.example.testing_hub.service;

import org.example.testing_hub.entity.User;
import org.example.testing_hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Получить всех пользователей.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Найти пользователя по ID.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден с ID: " + id));
    }

    /**
     * Найти пользователя по логину.
     */
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден с логином: " + login));
    }

    /**
     * Найти пользователя по email.
     */


    /**
     * Получить пользователей по роли.
     */
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    /**
     * Создать нового пользователя.
     */
    public User createUser(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new RuntimeException("Пользователь с логином " + user.getLogin() + " уже существует");
        }

        return userRepository.save(user);
    }

    /**
     * Обновить данные пользователя.
     */
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден с ID: " + id));

        // Обновление полей
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getLogin() != null) {
            // Проверка, что логин уникален
            if (userRepository.findByLogin(updatedUser.getLogin()).isPresent() &&
                    !existingUser.getLogin().equals(updatedUser.getLogin())) {
                throw new RuntimeException("Пользователь с логином " + updatedUser.getLogin() + " уже существует");
            }
            existingUser.setLogin(updatedUser.getLogin());
        }
        // Добавьте другие поля при необходимости

        return userRepository.save(existingUser);
    }

    /**
     * Удалить пользователя по ID.
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Пользователь не найден с ID: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Проверить существование пользователя по логину.
     */
    public boolean userExistsByLogin(String login) {
        return userRepository.findByLogin(login).isPresent();
    }
}
