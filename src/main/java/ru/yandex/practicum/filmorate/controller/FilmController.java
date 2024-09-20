package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.Validator;

import java.util.*;

/**
 * Класс FilmController представляет собой контроллер, который обрабатывает запросы к фильмам.
 */
@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final Validator validator;

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
        validator.validateFilm(newFilm);
        filmService.createFilm(newFilm);
        log.info("Фильм: {} добавлен.", newFilm.getName());
        return newFilm;
    }

    /**
     * Метод updateFilm обновляет данные фильма на основе данных из запроса.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@RequestBody Film updatedFilm) {
        validator.validateFilm(updatedFilm);
        filmService.updateFilm(updatedFilm);
        return updatedFilm;
    }

    /**
     * Метод getFilmById возвращает фильм с идентификатором id.
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film getFilmById(@PathVariable long id) {
        log.debug("Запрос на получения фильма по айди");
        return filmService.getFilmById(id);
    }

    /**
     * Метод addLike добавляет лайк фильму с идентификатором id от пользователя с идентификатором userId.
     */
    @PutMapping("{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    /**
     * Метод deleteLike удаляет лайк фильму с идентификатором id от пользователя с идентификатором userId.
     */
    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }

    /**
     * Метод getPopularFilms возвращает список популярных фильмов.
     * Количество фильмов в списке задаётся параметром запроса count, по умолчанию — 10.
     */
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getLikedFilms(count);
    }

}
