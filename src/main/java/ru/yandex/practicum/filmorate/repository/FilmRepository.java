package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    Film createFilm(Film data);

    Film updateFilm(Film data);

    List<Film> getAllFilms();

    Collection<Film> getLikedFilms(int count);

    Optional<Film> getFilmById(Long id);

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
}
