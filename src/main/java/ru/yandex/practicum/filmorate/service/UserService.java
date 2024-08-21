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

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        User user;
        try {
            user = userStorage.getUserById(userId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Пользователь с id: " + userId + " не найден");
        }
        User friend;
        try {
            friend = userStorage.getUserById(friendId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Пользователь с id: " + friendId + " не найден");
        }
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        User user = userStorage.getUserById(userId);
        User otherUser = userStorage.getUserById(otherUserId);
        Set<Long> friend1Friends = user.getFriends();
        Set<Long> friends2Friends = otherUser.getFriends();
        List<User> commonFriends = new ArrayList<>();
        for (Long idFriends : friend1Friends) {
            if (friends2Friends.contains(idFriends)) {
                commonFriends.add(userStorage.getUserById(idFriends));
            }
        }
        return commonFriends;
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User newUser) {
        userStorage.createUser(newUser);
        return newUser;
    }

    public User updateUser(User updatedUser) {
        userStorage.updateUser(updatedUser);
        return updatedUser;
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    public List<User> getUserFriends(Long id) {
        Set<Long> friendsId = userStorage.getUserById(id).getFriends();
        if (friendsId.isEmpty()) {
            throw new NotFoundException("Список друзей пользователя с id:" + id + " пуст");
        }
        List<User> users = new ArrayList<>();
        friendsId.stream()
                .map(userStorage::getUserById)
                .forEach(users::add);
        return users;
    }
}
