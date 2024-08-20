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

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        if (!(user.getFriends().contains(friend.getId()))) {
            throw new NotFoundException(user.getName() + " не является другом для " + friend.getName());
        }
        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        User user = getUserById(userId);
        User otherUser = getUserById(otherUserId);
        Set<Long> friend1Friends = user.getFriends();
        Set<Long> friends2Friends = otherUser.getFriends();
        return friend1Friends.stream()
                .filter(friends2Friends::contains)
                .map(this::getUserById)
                .toList();
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
        List<User> users = new ArrayList<>();
        friendsId.stream()
                .map(userStorage::getUserById)
                .forEach(users::add);
        return users;
    }
}
