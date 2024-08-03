package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Класс UserController представляет собой контроллер, который обрабатывает запросы к пользователям.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long idCount = 0;

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

    /**
     * Метод validateUser проверяет корректность данных пользователя
     */
    private void validateUser(User validationUser) {
        if (validationUser.getEmail().isEmpty() || validationUser.getEmail().isBlank()) {
            log.error("Поле @mail пустое");
            throw new NotFoundException("@mail не может быть пустым!");
        }
        if (!validationUser.getEmail().contains("@")) {
            log.error("Поле @mail не содержит символ \"@\"");
            throw new ValidationException("@mail должен содержать символ \"@\" !");
        }
        if (validationUser.getLogin().isBlank() || validationUser.getLogin().isEmpty()) {
            log.error("Поле логин пустое");
            throw new ValidationException("Логин не может быть пустым!");
        }
        if (validationUser.getLogin().contains(" ")) {
            log.error("Поле логин содержит пробелы");
            throw new ValidationException("Логин не должен содержать пробелы");
        }
        if (validationUser.getBirthday().isAfter(LocalDate.now())) {
            log.error("Поле дата рожждения больше текущей даты");
            throw new ValidationException("Дата рождения не может быть больше текущей даты!");
        }
        if (isNull(validationUser.getName()) || validationUser.getName().isEmpty()) {
            log.warn("Пустое поле имя заменено полем логин");
            validationUser.setName(validationUser.getLogin());
        }
    }

    private long getNextId() {
        return idCount++;
    }
}
