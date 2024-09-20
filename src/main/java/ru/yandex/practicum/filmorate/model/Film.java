package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;

/**
 * Класс Film представляет собой объект фильма.
 */
@Data
@RequiredArgsConstructor
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Integer likes = 0;
    private LinkedHashSet<Genre> genres;
    private Rating mpa;
}
