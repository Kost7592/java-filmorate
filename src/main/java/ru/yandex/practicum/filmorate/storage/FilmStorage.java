package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> getAllFilms();

    Film createFilm(@RequestBody Film newFilm);

    Film updateFilm(@RequestBody Film updatedFilm);

    Film getFilmById(Long id);
}
