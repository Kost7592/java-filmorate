package ru.yandex.practicum.filmorate.exception;

/**
 * Класс ValidationException представляет собой исключение, которое возникает при попытке сохранить объект с
 * некорректными данными.
 */
public class ValidationException extends RuntimeException {

    /**
     Создает новый экземпляр исключения с указанным сообщением.
     * @param message сообщение об ошибке
     */
    public ValidationException(String message) {
        super(message);
    }
}
