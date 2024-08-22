package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    Collection<User> getAllUsers();

    User createUser(User newUser);

    User updateUser(@RequestBody User updatedUser);

    User getUserById(Long id);
}
