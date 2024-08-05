package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ru.yandex.practicum.filmorate.controller.Validator.validateUser;

/**
 * Класс UserController представляет собой контроллер, который обрабатывает запросы к пользователям.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long idCount = 1;

    /**
     * Метод getAllUsers возвращает всех пользователей из коллекции users.
     */
    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Метод createUser создает пользователя на основе данных из запроса.
     */
    @PostMapping
    public User createUser(@RequestBody User newUser) {
        validateUser(newUser);
        newUser.setId(getNextId());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    /**
     * Метод updateUser обновляет данные пользователя на основе данных из запроса.
     */
    @PutMapping
    public User updateUser(@RequestBody User updatedUser) {
        if (users.containsKey(updatedUser.getId())) {
            validateUser(updatedUser);
            users.replace(updatedUser.getId(), updatedUser);
            return updatedUser;
        }
        throw new NotFoundException("Пользователь с таким id: " + updatedUser.getId() + " не найден!");
    }

    private long getNextId() {
        return idCount++;
    }
}
