package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import(FilmRepositoryJdbc.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmRepositoryJdbcTest {
    private final FilmRepository filmRepository;

    @Test
    public void shouldReturnFilmById() {
        Optional<Film> userOptional = filmRepository.getFilmById(1L);
        Film film = userOptional.get();
        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(compareTestFilm());
    }

    @Test
    public void shouldCreateFilm() {
        Film filmNew = filmRepository.createFilm(createTestFilm(1L));
        assertThat(filmNew)
                .usingRecursiveComparison()
                .isEqualTo(createTestFilm(2L));
    }

    @Test
    public void shouldUpdateFilm() {
        Optional<Film> filmOptional = filmRepository.getFilmById(1L);
        Film filmNewName = filmOptional.get();
        filmNewName.setName("Новое_Имя_Фильма");
        filmRepository.updateFilm(filmNewName);
        filmOptional = filmRepository.getFilmById(1L);
        assertThat(filmOptional.get().getName())
                .isEqualTo("Новое_Имя_Фильма");
    }

    @Test
    public void shouldAddLikeToFilm() {
        filmRepository.addLike(1L, 1L);
        List<Film> filmList = filmRepository.getLikedFilms(1).stream().toList();
        assertThat(filmList.get(0).getId())
                .isEqualTo(1);
    }

    static Rating testRating() {
        Rating rating = new Rating();
        rating.setId(3L);
        rating.setName("PG-13");
        return rating;
    }

    static Genre testGenre() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Боевик");
        return genre;
    }

    static Film compareTestFilm() {
        LinkedHashSet<Genre> setGenres = new LinkedHashSet<>();
        setGenres.add(testGenre());
        Film film = new Film();
        film.setId(1L);
        film.setDescription("Научно-фантастический фильм Джеймса Кэмерона");
        film.setName("Аватар");
        film.setMpa(testRating());
        film.setGenres(setGenres);
        film.setDuration(162);
        film.setReleaseDate(LocalDate.of(2009, 12, 10));
        return film;
    }

    static Film createTestFilm(Long id) {
        LinkedHashSet<Genre> setGenres = new LinkedHashSet<>();
        setGenres.add(testGenre());
        Film film = new Film();
        if (id != 0) {
            film.setId(id);
        }
        film.setDescription("Описание к фильму");
        film.setName("Фильм новый");
        film.setMpa(testRating());
        film.setGenres(setGenres);
        film.setDuration(162);
        film.setReleaseDate(LocalDate.of(2009, 12, 10));
        return film;
    }
}