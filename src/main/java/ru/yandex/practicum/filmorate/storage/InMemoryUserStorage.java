package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ru.yandex.practicum.filmorate.storage.Validator.validateUser;

/**
 * Класс InMemoryUserStorage — это компонент, который реализует функциональность UserStorage.
 * Он обеспечивает хранение информации о пользователях в памяти приложения.
 */
@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long idCount = 1;

    /**
     * Метод getAllUsers возвращает всех пользователей хеш таблицы users.
     */
    @Override
    public Collection<User> getAllUsers() {
        log.info("Получена вся коллекция пользователей.");
        return users.values();
    }

    /**
     * Метод createUser создает нового пользователя.
     */
    @Override
    public User createUser(User newUser) {
        validateUser(newUser);
        newUser.setId(getNextId());
        users.put(newUser.getId(), newUser);
        log.info("Новый пользователь: {} создан", newUser.getName());
        return newUser;
    }

    /**
     * Метод updateUser обновляет информацию о пользователе.
     */
    @Override
    public User updateUser(User updatedUser) {
        if (users.containsKey(updatedUser.getId())) {
            validateUser(updatedUser);
            users.replace(updatedUser.getId(), updatedUser);
            log.info("Информация о пользователе {} обновлена", updatedUser.getName());
            return updatedUser;
        }
        log.error("Пользователь с таким id: {} + не найден!", updatedUser.getId());
        throw new NotFoundException("Пользователь с таким id: " + updatedUser.getId() + " не найден!");
    }

    /**
     * Метод getUserById возвращает пользователя по идентификатору id.
     */
    @Override
    public User getUserById(Long id) {
        if (!users.containsKey(id)) {
            log.error("Пользователь с id {} не существует", id);
            throw new NotFoundException("Пользователь с id " + id + " не существует");
        }
        return users.get(id);
    }

    private long getNextId() {
        return idCount++;
    }
}
