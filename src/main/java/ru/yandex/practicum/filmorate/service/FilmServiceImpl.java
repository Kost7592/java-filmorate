package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.GenreRepository;
import ru.yandex.practicum.filmorate.repository.RatingRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.Collection;

import java.util.List;

/**
 * FilmServiceImpl — это сервис, который реализует функциональность FilmService.
 * Он обеспечивает взаимодействие с FilmStorage и UserStorage для хранения фильмов и информации о пользователях
 * соответственно.
 */
@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final RatingRepository ratingRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    /**
     * Метод addLike добавляет лайк фильму с идентификатором filmId от пользователя с идентификатором userId.
     */
    @Override
    public void addLike(Long filmId, Long userId) {
        User user = userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Пользователь с " + userId
                + "не найден"));
        filmRepository.addLike(filmId, userId);
    }

    /**
     * Метод deleteLike удаляет лайк фильму с идентификатором filmId от пользователя с идентификатором userId.
     */
    @Override
    public void deleteLike(Long filmId, Long userId) {
        User user = userRepository.getById(userId).orElseThrow(() -> new NotFoundException("Пользователь с " + userId
                + "не найден"));
        filmRepository.deleteLike(filmId, userId);
    }

    /**
     * Метод getLikedFilms возвращает список популярных фильмов. Количество фильмов в списке задаётся параметром count.
     */
    @Override
    public Collection<Film> getLikedFilms(Integer count) {
        return filmRepository.getLikedFilms(count);
    }

    /**
     * Метод getAllFilms возвращает все фильмы из коллекции films.
     */
    @Override
    public Collection<Film> getAllFilms() {
        return filmRepository.getAllFilms();
    }

    /**
     * Метод createFilm создает новый фильм.
     */
    @Override
    public Film createFilm(Film newFilm) {
        if (newFilm.getGenres() != null) {
            final List<Long> genreIds = newFilm.getGenres().stream().map(Genre::getId).toList();
            final Collection<Genre> genres = genreRepository.findByIds(genreIds);
            if (genres.size() != genreIds.size()) {
                throw new ValidationException("Жанры не найдены");
            }
        }
        ratingRepository.findById(newFilm.getMpa().getId())
                .orElseThrow(() -> new ValidationException("MPA с " + newFilm.getMpa().getId() + "не найден"));
        return filmRepository.createFilm(newFilm);
    }

    /**
     * Метод updateFilm обновляет данные фильма.
     */
    @Override
    public Film updateFilm(Film updatedFilm) {
        filmRepository.getFilmById(updatedFilm.getId())
                .orElseThrow(() -> new NotFoundException("Фильм с " + updatedFilm.getId() + "не найден"));

        ratingRepository.findById(updatedFilm.getMpa().getId())
                .orElseThrow(() -> new NotFoundException("MPA с " + updatedFilm.getMpa().getId() + "не найден"));

        if (updatedFilm.getGenres() != null) {

            final List<Long> genreIds = updatedFilm.getGenres().stream().map(Genre::getId).toList();
            final Collection<Genre> genres = genreRepository.findByIds(genreIds);
            if (genres.size() != genreIds.size()) {
                throw new ValidationException("Для фильма " + updatedFilm.getId() + " указаны несуществующие жанры");
            }
        }
        return filmRepository.updateFilm(updatedFilm);
    }

    @Override
    public Film getFilmById(long id) {
        final Film film = filmRepository.getFilmById(id)
                .orElseThrow(() -> new NotFoundException("Фильм с " + id + "не найден"));

        return film;
    }
}
