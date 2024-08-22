package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

/**
 * Класс UserController представляет собой контроллер, который обрабатывает запросы к пользователям.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод getAllUsers возвращает всех пользователей из коллекции users.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Метод createUser создает пользователя на основе данных из запроса.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User newUser) {
        userService.createUser(newUser);
        return newUser;
    }

    /**
     * Метод updateUser обновляет данные пользователя на основе данных из запроса.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User updatedUser) {
        userService.updateUser(updatedUser);
        return updatedUser;
    }

    /**
     * Метод getUserById возвращает пользователя с идентификатором id.
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    /**
     * Метод addFriendship добавляет в список друзей пользователя с идентификатором id пользователя с идентификатором
     * friendId. И аналогично пользователю с идентификатором friendId в список друзей пользователя с идентификатором id.
     */
    @PutMapping("{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriendship(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
    }

    /**
     * Метод deleteFriendship удаляет из списка друзей пользователя с идентификатором id пользователя с
     * идентификатором friendId. И аналогично у пользователя с идентификатором friendId из списка друзей пользователя с
     * идентификатором id.
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriendship(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
    }

    /**
     * Метод getUserFriends возвращает список всех друзей пользователя с идентификатором id/
     */
    @GetMapping("{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUserFriends(@PathVariable Long id) {
        return userService.getUserFriends(id);
    }

    /**
     * Метод getCommonFriends возвращает список общих друзей пользователей с идентификаторами id и otherId.
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }


}
