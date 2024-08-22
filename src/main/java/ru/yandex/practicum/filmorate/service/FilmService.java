package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmService {

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    List<Film> getLikedFilms(Integer count);

    Collection<Film> getAllFilms();

    Film createFilm(Film newFilm);

    Film updateFilm(Film updatedFilm);
}
