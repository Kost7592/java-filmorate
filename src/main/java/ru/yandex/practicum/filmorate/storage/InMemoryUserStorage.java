package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ru.yandex.practicum.filmorate.storage.Validator.validateUser;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long idCount = 1;

    /**
     * Метод getAllUsers возвращает всех пользователей из коллекции users.
     */
    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Метод createUser создает пользователя на основе данных из запроса.
     */
    @Override
    public User createUser(User newUser) {
        validateUser(newUser);
        newUser.setId(getNextId());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    /**
     * Метод updateUser обновляет данные пользователя на основе данных из запроса.
     */
    @Override
    public User updateUser(@RequestBody User updatedUser) {
        if (users.containsKey(updatedUser.getId())) {
            validateUser(updatedUser);
            users.replace(updatedUser.getId(), updatedUser);
            return updatedUser;
        }
        throw new NotFoundException("Пользователь с таким id: " + updatedUser.getId() + " не найден!");
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
    }

    private long getNextId() {
        return idCount++;
    }
}
