package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Collection<Genre> getAllGenres() {
        return genreRepository.getGenreList();
    }

    @Override
    public Genre getById(long id) {
        final Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Жанр с " + id + "не найден"));
        return genre;
    }
}