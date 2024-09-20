package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;
import java.util.Optional;

public interface RatingRepository {
    Optional<Rating> findById(Long id);

    Collection<Rating> getAllMpa();

    Rating create(Rating rating);

    void delete(Rating rating);

    Rating update(Rating rating);
}
