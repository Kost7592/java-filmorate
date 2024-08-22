package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import ru.yandex.practicum.filmorate.model.ErrorResponse;

/**
 * Класс ErrorHandler обрабатывает исключения, возникающие в процессе работы приложения.
 * Он предоставляет централизованное место для обработки исключений и позволяет возвращать
 * согласованные ответы об ошибках пользователям.
 */
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Обрабатывает исключение NotFoundException, возвращая ответ с кодом 404 (Not Found).
     * В ответе содержится сообщение об ошибке, полученное из исключения.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNull(final NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Обрабатывает исключение ValidationException, возвращая ответ с кодом 400 (Bad Request).
     * В ответе содержится сообщение об ошибке, полученное из исключения.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidateException(final ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Обрабатывает любое исключение, возвращая ответ с кодом 500 (Internal Server Error).
     * В ответе содержится сообщение об ошибке, полученное из исключения.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOther(final Throwable e) {
        return new ErrorResponse(e.getMessage());
    }
}
