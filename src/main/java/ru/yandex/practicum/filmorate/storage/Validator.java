package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static java.util.Objects.isNull;

/**
 * Класс Validator предоставляет методы проверки корректности данных.
 */
@Slf4j
public class Validator {
    private static final LocalDate FILM_REFERENCE_POINT = LocalDate.of(1895, 12, 28);
    private static final int DESCRIPTION_LENGTH = 200;

    private Validator() {
    } ///закрытый конструктор, предотвращает создание экземпляров класса

    /**
     * Метод validateUser проверяет корректность данных пользователя.
     */
    protected static void validateUser(User validationUser) {
        if (validationUser.getEmail().isEmpty() || validationUser.getEmail().isBlank()) {
            log.error("Поле @mail пустое");
            throw new NotFoundException("@mail не может быть пустым!");
        }
        if (!validationUser.getEmail().contains("@")) {
            log.error("Поле @mail не содержит символ \"@\"");
            throw new ValidationException("@mail должен содержать символ \"@\" !");
        }
        if (validationUser.getLogin().isBlank() || validationUser.getLogin().isEmpty()) {
            log.error("Поле логин пустое");
            throw new ValidationException("Логин не может быть пустым!");
        }
        if (validationUser.getLogin().contains(" ")) {
            log.error("Поле логин содержит пробелы");
            throw new ValidationException("Логин не должен содержать пробелы");
        }
        if (validationUser.getBirthday().isAfter(LocalDate.now())) {
            log.error("Поле дата рожждения больше текущей даты");
            throw new ValidationException("Дата рождения не может быть больше текущей даты!");
        }
        if (isNull(validationUser.getName()) || validationUser.getName().isEmpty()) {
            log.warn("Пустое поле имя заменено полем логин");
            validationUser.setName(validationUser.getLogin());
        }
    }

    /**
     * Метод validateFilm проверяет корректность данных фильма.
     */
    protected static void validateFilm(Film validationFilm) {
        if (validationFilm.getName().isEmpty() || validationFilm.getName().isBlank()) {
            log.error("Поле имя пустое.");
            throw new ValidationException("Имя не может быть пустым!");
        }
        if (validationFilm.getDescription().length() > DESCRIPTION_LENGTH) {
            log.error("Поле описания больше {} символов.", DESCRIPTION_LENGTH);
            throw new ValidationException("Описания не может быть длиннее " + DESCRIPTION_LENGTH + "символов!");
        }
        if (validationFilm.getReleaseDate().isBefore(FILM_REFERENCE_POINT)) {
            log.error("Дата релиза раньше {} .", FILM_REFERENCE_POINT);
            throw new ValidationException("Дата релиза фильма не может быть раньше " + FILM_REFERENCE_POINT + "!");
        }
        if (validationFilm.getDuration() <= 0) {
            log.error("Продолжительность фильма ноль или отрицательное число.");
            throw new ValidationException("Продолжительность фильма не может быть нулем или отрицательным числом");
        }
    }
}
