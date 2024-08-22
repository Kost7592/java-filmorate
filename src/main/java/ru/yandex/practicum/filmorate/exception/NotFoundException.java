package ru.yandex.practicum.filmorate.exception;

/**
 * Класс NotFoundException представляет собой исключение, которое возникает при попытке доступа к объекту,
 * не найденному в системе.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Создает новый экземпляр исключения с указанным сообщением.
     * @param message сообщение об ошибке
     */
    public NotFoundException(String message) {
        super(message);
    }
}
