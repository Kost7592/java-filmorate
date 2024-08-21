package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс User представляет собой объект пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();
}
