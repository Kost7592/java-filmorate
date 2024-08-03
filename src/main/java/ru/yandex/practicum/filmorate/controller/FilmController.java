package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

/**
 * Класс FilmController представляет собой контроллер, который обрабатывает запросы к фильмам.
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long idCount = 0;
    private static final LocalDate FILM_REFERENCE_POINT = LocalDate.of(1895, 12, 28);
    private static final int DESCRIPTION_LENGTH = 200;

    /**
     * Метод getAllFilms возвращает все фильмы из коллекции films.
     */
    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("Получена вся коллекция фильмов.");
        return films.values();
    }

    /**
     * Метод createFilm создает фильм на основе данных из запроса.
     */
    @PostMapping
    public Film createFilm(@RequestBody Film newFilm) {
        validateFilm(newFilm);
        newFilm.setId(getNextId());
        films.put(newFilm.getId(), newFilm);
        log.info("Фильм: {} добавлен.", newFilm.getName());
        return newFilm;
    }

    /**
     * Метод updateFilm обновляет данные фильма на основе данных из запроса.
     */
    @PutMapping
    public Film updateFilm(@RequestBody Film updatedFilm) {
        if (films.containsKey(updatedFilm.getId())) {
            validateFilm(updatedFilm);
            films.replace(updatedFilm.getId(), updatedFilm);
            log.info("Фильм {} обновлен.", updatedFilm.getName());
            return updatedFilm;
        }
        log.error("Фильм с данным id: {} не найден.", updatedFilm.getId());
        throw new NotFoundException("Фильм с данным id:" + updatedFilm.getId() + " не найден!");
    }

    /**
     * Метод validateFilm проверяет корректность данных фильма
     */
    private void validateFilm(Film validationFilm) {
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

    private long getNextId() {
        log.trace("Id счетчик увеличен на 1");
        return idCount++;
    }
}
