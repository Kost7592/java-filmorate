package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserServiceImpl — это сервис, который реализует функциональность UserService.
 * Он обеспечивает взаимодействие с UserStorage для хранения информации о пользователях.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    /**
     * Метод addFriend добавляет в множество друзей пользователя с идентификатором userId пользователя с
     * идентификатором friendId. И аналогично пользователю с идентификатором friendId в множество друзей
     * пользователя с идентификатором id.
     */
    @Override
    public void addFriend(Long userId, Long friendId) {
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id: " + userId + " не найден");
        }
        User friend = userStorage.getUserById(friendId);
        if (friend == null) {
            throw new NotFoundException("Пользователь с id: " + friendId + " не найден");
        }
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    /**
     * Метод deleteFriend удаляет из множества друзей пользователя с идентификатором userId пользователя с
     * идентификатором friendId. И аналогично у пользователя с идентификатором friendId из множества друзей
     * пользователя с идентификатором id.
     */
    @Override
    public void deleteFriend(Long userId, Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    /**
     * Метод getCommonFriends возвращает список общих друзей пользователей с идентификаторами userId и otherUserId.
     */
    @Override
    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        User user = userStorage.getUserById(userId);
        User otherUser = userStorage.getUserById(otherUserId);
        Set<Long> friend1Friends = user.getFriends();
        Set<Long> friends2Friends = otherUser.getFriends();
        return friend1Friends.stream()
                .filter(friends2Friends::contains)
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    /**
     * Метод getAllUsers возвращает всех пользователей из коллекции users.
     */
    @Override
    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    /**
     * Метод createUser создает нового пользователя.
     */
    @Override
    public User createUser(User newUser) {
        userStorage.createUser(newUser);
        return newUser;
    }

    /**
     * Метод updateUser обновляет данные пользователя.
     */
    @Override
    public User updateUser(User updatedUser) {
        userStorage.updateUser(updatedUser);
        return updatedUser;
    }

    /**
     * Метод getUserById возвращает пользователя с идентификатором id.
     */
    @Override
    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    /**
     * Метод getUserFriends возвращает список всех друзей пользователя с идентификатором id.
     */
    @Override
    public List<User> getUserFriends(Long id) {
        Set<Long> friendsId = userStorage.getUserById(id).getFriends();

        List<User> users = new ArrayList<>();
        friendsId.stream()
                .map(userStorage::getUserById)
                .forEach(users::add);
        return users;
    }
}
