package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.Collection;
import java.util.List;

/**
 * UserServiceImpl — это сервис, который реализует функциональность UserService.
 * Он обеспечивает взаимодействие с UserStorage для хранения информации о пользователях.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Метод addFriend добавляет в множество друзей пользователя с идентификатором userId пользователя с
     * идентификатором friendId. И аналогично пользователю с идентификатором friendId в множество друзей
     * пользователя с идентификатором id.
     */
    @Override
    public void addFriend(Long userId, Long friendId) {
        userRepository.addFriend(getUserById(userId), getUserById(friendId));
    }

    /**
     * Метод deleteFriend удаляет из множества друзей пользователя с идентификатором userId пользователя с
     * идентификатором friendId. И аналогично у пользователя с идентификатором friendId из множества друзей
     * пользователя с идентификатором id.
     */
    @Override
    public void deleteFriend(Long userId, Long friendId) {
        userRepository.deleteFriend(getUserById(userId), getUserById(friendId));
    }

    /**
     * Метод getCommonFriends возвращает список общих друзей пользователей с идентификаторами userId и otherUserId.
     */
    @Override
    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        final User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с " + userId + "не найден"));
        final User userOther = userRepository.getById(otherUserId)
                .orElseThrow(() -> new NotFoundException("Пользователь с " + otherUserId + "не найден"));
        return userRepository.getCommonFriends(user, userOther);
    }

    /**
     * Метод getAllUsers возвращает всех пользователей из коллекции users.
     */
    @Override
    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    /**
     * Метод createUser создает нового пользователя.
     */
    @Override
    public User createUser(User newUser) {
        return userRepository.createUser(newUser);
    }

    /**
     * Метод updateUser обновляет данные пользователя.
     */
    @Override
    public User updateUser(User updatedUser) {
        final User user = userRepository.getById(updatedUser.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь с с id:" + updatedUser.getId() + "не найден"));
        return userRepository.updateUser(updatedUser);
    }

    /**
     * Метод getUserById возвращает пользователя с идентификатором id.
     */
    @Override
    public User getUserById(Long id) {
        final User user = userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id:" + id + "не найден"));
        return user;
    }

    /**
     * Метод getUserFriends возвращает список всех друзей пользователя с идентификатором id.
     */
    @Override
    public List<User> getUserFriends(Long id) {
        return userRepository.getFriends(getUserById(id));
    }
}
