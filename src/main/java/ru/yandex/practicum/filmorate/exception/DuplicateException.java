package ru.yandex.practicum.filmorate.exception;

/**
 * Класс DuplicateException представляет собой исключение, которое возникает при попытке добавить объект, который уже
 * существует в системе.
 */
public class DuplicateException extends RuntimeException {

    /**
     * Создает новый экземпляр исключения с указанным сообщением.
     * @param message сообщение об ошибке
     */
    public DuplicateException(String message) {
        super(message);
    }
}
