package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> getCommonFriends(Long userId, Long otherUserId);

    Collection<User> getAllUsers();

    User createUser(User newUser);

    User updateUser(User updatedUser);

    User getUserById(Long id);

    List<User> getUserFriends(Long id);
}
