package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс ErrorResponse представляет собой ответ об ошибке, который возвращается пользователю.
 * Он содержит сообщение об ошибке в виде строки.
 */
@Getter
@Setter
public class ErrorResponse {
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}
