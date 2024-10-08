package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ru.yandex.practicum.filmorate.storage.Validator.validateFilm;

/**
 * Класс InMemoryFilmStorage — это компонент, который реализует функциональность FilmStorage.
 * Он обеспечивает хранение информации о фильмах в памяти приложения.
 */
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long idCount = 1;

    /**
     * Метод getAllFilms возвращает все фильмы хеш таблицы films.
     */
    @Override
    public Collection<Film> getAllFilms() {
        log.info("Получена вся коллекция фильмов.");
        return films.values();
    }

    /**
     * Метод createFilm создает новый фильм.
     */

    @Override
    public Film createFilm(Film newFilm) {
        validateFilm(newFilm);
        newFilm.setId(getNextId());
        films.put(newFilm.getId(), newFilm);
        log.info("Фильм: {} добавлен.", newFilm.getName());
        return newFilm;
    }

    /**
     * Метод updateFilm обновляет информацию о фильме.
     */
    @Override
    public Film updateFilm(Film updatedFilm) {
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
     * Метод getFilmById возвращает фильм по идентификатору id.
     */
    @Override
    public Film getFilmById(Long id) {
        if (!films.containsKey(id)) {
            log.error("Нельзя получить фильм с данным id: {}, так как он не найден.", id);
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
        return films.get(id);
    }

    private long getNextId() {
        log.trace("Id счетчик увеличен на 1");
        return idCount++;
    }
}
