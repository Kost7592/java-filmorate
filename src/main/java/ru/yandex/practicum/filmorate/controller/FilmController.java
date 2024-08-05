package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

import static ru.yandex.practicum.filmorate.controller.Validator.validateFilm;

/**
 * Класс FilmController представляет собой контроллер, который обрабатывает запросы к фильмам.
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long idCount = 1;

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

    private long getNextId() {
        log.trace("Id счетчик увеличен на 1");
        return idCount++;
    }
}
