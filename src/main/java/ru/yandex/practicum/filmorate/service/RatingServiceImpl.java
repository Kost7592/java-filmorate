package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.RatingRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    @Override
    public Collection<Rating> getAllMpa() {
        return ratingRepository.getAllMpa();
    }

    @Override
    public Rating findById(long id) {
        final Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MPA с " + id + "не найден"));
        return rating;
    }
}
