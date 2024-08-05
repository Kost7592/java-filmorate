package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

/**
 * Класс FilmControllerTest представляет собой тестовый класс, проверяющий работу класса UserController.
 */
public class UserControllerTest {
    User user;

    /**
     * Метод validateNotCreateUserWithEmptyEmail проверяет поведение класса UserController при добавлении пользователя
     * с пустым @mail.
     */
    @Test
    void validateNotCreateUserWithEmptyEmail() {
        user = User.builder()
                .email("")
                .login("Login")
                .name("Name")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        UserController userController = new UserController();
        Assertions.assertThrows(NotFoundException.class, () -> {
            userController.createUser(user);
        }, "Поле @mail пустое");
    }

    /**
     * Метод validateNotCreateUserWithEmptyLogin проверяет поведение класса UserController при добавлении пользователя
     * с пустым логином.
     */
    @Test
    void validateNotCreateUserWithEmptyLogin() {
        user = User.builder()
                .email("email@yandex.ru")
                .login("")
                .name("Name")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        UserController userController = new UserController();
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        }, "Поле логин пустое");
    }

    /**
     * Метод validateNotCreateUserWithWrongBirthday проверяет поведение класса UserController при добавлении
     * пользователя с днем рождения больше текущей даты.
     */
    @Test
    void validateNotCreateUserWithWrongBirthday() {
        user = User.builder()
                .email("email@yandex.ru")
                .login("Login")
                .name("Name")
                .birthday(LocalDate.of(2025, 1, 1))
                .build();
        UserController userController = new UserController();
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        }, "Поле дата рожждения больше текущей даты");
    }

    /**
     * Метод validateCreateUserWithoutName проверяет поведение класса UserController при добавлении пользователя без
     * имени.
     */
    @Test
    void validateCreateUserWithoutName() {
        user = User.builder()
                .email("email@yandex.ru")
                .login("Login")
                .name("")
                .birthday(LocalDate.of(2000, 6, 4))
                .build();

        UserController userController = new UserController();
        User userCreate = userController.createUser(user);
        Assertions.assertEquals(userCreate.getName(), userCreate.getLogin(),
                "Пустое поле имя заменено полем логин");
    }
}