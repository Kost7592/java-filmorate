package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import(RatingRepositoryJdbc.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("JdbcRatingRepository")
public class RatingRepositoryJdbcTest {
    private final RatingRepositoryJdbc ratingRepository;

    @Test
    public void shouldGetAllMPA() {
        List<Rating> listMPA = ratingRepository.getAllMpa().stream().toList();
        assertThat(listMPA.size())
                .isEqualTo(5);
    }
}
