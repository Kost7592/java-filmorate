package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

import static ru.yandex.practicum.filmorate.storage.Validator.validateFilm;

/**
 * Класс FilmController представляет собой контроллер, который обрабатывает запросы к фильмам.
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
     * Метод getAllFilms возвращает все фильмы из коллекции films.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getAllFilms() {
        log.info("Получена вся коллекция фильмов.");
        return filmService.getAllFilms();
    }

    /**
     * Метод createFilm создает фильм на основе данных из запроса.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@RequestBody Film newFilm) {
        filmService.createFilm(newFilm);
        log.info("Фильм: {} добавлен.", newFilm.getName());
        return newFilm;
    }

    /**
     * Метод updateFilm обновляет данные фильма на основе данных из запроса.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Film updateFilm(@RequestBody Film updatedFilm) {
        filmService.updateFilm(updatedFilm);
        return updatedFilm;
    }

    @PutMapping("{id}/like/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular?count={count}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        filmService.get
    }

}
