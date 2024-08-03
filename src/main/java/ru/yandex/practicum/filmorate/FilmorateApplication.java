package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс FilmorateApplication является точкой входа в приложение.
 */
@SpringBootApplication
public class FilmorateApplication {
    /**
     * Метод main является точкой входа приложения Spring Boot. Он запускает приложение, передавая ему аргументы
     * командной строки.
     */
    public static void main(String[] args) {

        SpringApplication.run(FilmorateApplication.class, args);
    }

}
