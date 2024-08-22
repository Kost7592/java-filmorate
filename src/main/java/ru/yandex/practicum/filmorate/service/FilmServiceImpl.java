package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FilmServiceImpl — это сервис, который реализует функциональность FilmService.
 * Он обеспечивает взаимодействие с FilmStorage и UserStorage для хранения фильмов и информации о пользователях
 * соответственно.
 */
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    /**
     * Метод addLike добавляет лайк фильму с идентификатором filmId от пользователя с идентификатором userId.
     */
    @Override
    public void addLike(Long filmId, Long userId) {
        if (userStorage.getUserById(userId) == null) {
            throw new NotFoundException("Пользователя с таким id:" + userId + " не существует");
        }
        Film film = filmStorage.getFilmById(filmId);
        film.getLikes().add(userId);
    }

    /**
     * Метод deleteLike удаляет лайк фильму с идентификатором filmId от пользователя с идентификатором userId.
     */
    @Override
    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        film.getLikes().remove(user.getId());
    }

    /**
     * Метод getLikedFilms возвращает список популярных фильмов. Количество фильмов в списке задаётся параметром count.
     */
    @Override
    public List<Film> getLikedFilms(Integer count) {
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * Метод getAllFilms возвращает все фильмы из коллекции films.
     */
    @Override
    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    /**
     * Метод createFilm создает новый фильм.
     */
    @Override
    public Film createFilm(Film newFilm) {
        filmStorage.createFilm(newFilm);
        return newFilm;
    }

    /**
     * Метод updateFilm обновляет данные фильма.
     */
    @Override
    public Film updateFilm(Film updatedFilm) {
        filmStorage.updateFilm(updatedFilm);
        return updatedFilm;
    }
}
