package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User createUser(User user);

    User updateUser(User newUser);

    Collection<User> getAllUsers();

    Optional<User> getById(long id);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    List<User> getFriends(User user);

    List<User> getCommonFriends(User user, User otherUser);
}
