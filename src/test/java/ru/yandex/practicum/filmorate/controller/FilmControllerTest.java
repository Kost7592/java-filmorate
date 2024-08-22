package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

/**
 * Класс FilmControllerTest представляет собой тестовый класс, проверяющий работу класса FilmController.
 */
@SpringBootTest
public class FilmControllerTest {
    Film film;

    @Autowired
    private FilmController filmController;
    /**
     * Метод validateNotAddFilmWithEmptyName проверяет поведение класса при добавлении фильма с пустым именем
     */
    @Test
    void validateNotAddFilmWithEmptyName() {
        film = Film.builder()
                .name("")
                .description("Some description about film")
                .releaseDate(LocalDate.of(2001, 1, 1))
                .duration(100)
                .build();
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.createFilm(film);
        }, "Поле имя пустое.");
    }

    /**
     * Метод validateNotAddFilmWithLongDescription проверяет поведение класса при добавлении фильма с описанием,
     * имеющим длину выше максимально заданного значения.
     */
    @Test
    void validateNotAddFilmWithLongDescription() {
        film = Film.builder()
                .name("Some Film")
                .description("Some description about film, with more than 200 characters. 00000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000")
                .releaseDate(LocalDate.of(2001, 1, 1))
                .duration(100)
                .build();
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.createFilm(film);
        }, "Поле описания больше 200 символов.");
    }

    /**
     * Метод validateNotAddFilmWithReleaseDateLaterThanNow проверяет работу класса при добавлении фильма с датой релиза
     * раньше установленной даты.
     */
    @Test
    void validateNotAddFilmWithReleaseDateLaterThanNow() {
        film = Film.builder()
                .name("Some Film")
                .description("Some description about film")
                .releaseDate(LocalDate.of(1800, 1, 1))
                .duration(100)
                .build();
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.createFilm(film);
        }, "Дата релиза раньше 1895-12-28");
    }

    /**
     * Метод validateNotAddFilmIfDurationEqualsZero проверяет работу класса при добавлении фильма с продолжительностью
     * равной нулю.
     */
    @Test
    void validateNotAddFilmIfDurationEqualsZero() {
        film = Film.builder()
                .name("Some Film")
                .description("Some description about film")
                .releaseDate(LocalDate.of(2001, 1, 1))
                .duration(0)
                .build();
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.createFilm(film);
        }, "Продолжительность равно нулю");
    }
}
