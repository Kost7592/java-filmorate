package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Класс User представляет собой объект пользователя.
 */
@Data
@Builder
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
