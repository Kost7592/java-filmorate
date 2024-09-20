package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import(UserRepositoryJdbc.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("JdbcUserRepository")
class UserRepositoryJdbcTest {
    private final UserRepository userRepository;
    public static final long FindUser_Id = 1L;

    @Test
    public void shouldReturnUserById() {
        Optional<User> userOptional = userRepository.getById(FindUser_Id);
        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(compareTestUser(FindUser_Id));
    }

    @Test
    public void shouldUpdateUser() {
        Optional<User> userOptional = userRepository.getById(1);
        User userNewName = userOptional.get();
        userNewName.setName("Новое_Имя");
        userRepository.updateUser(userNewName);
        userOptional = userRepository.getById(1);
        assertThat(userOptional.get().getName())
                .isEqualTo("Новое_Имя");
    }

    @Test
    public void shouldDeleteUser() {
        List<User> listUsers = userRepository.getAllUsers().stream().toList();
        assertThat(listUsers.size())
                .isEqualTo(2);
    }

    @Test
    public void shouldAddFriendToUser() {
        Optional<User> userOptional = userRepository.getById(1);
        Optional<User> friendOptional = userRepository.getById(2);
        User user = userOptional.get();
        User friend = friendOptional.get();
        userRepository.addFriend(user, friend);
        List<User> listFriends = userRepository.getFriends(user);
        assertThat(listFriends.size())
                .isEqualTo(1);
    }

    static User compareTestUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail("user1@mail.com");
        user.setLogin("User1Name");
        user.setName("User1Name");
        user.setBirthday(LocalDate.of(1990, 05, 07));
        return user;
    }

    static User createTestUser() {
        User user = new User();
        user.setEmail("user1@mail.com");
        user.setLogin("User1Name");
        user.setName("User1Name");
        user.setBirthday(LocalDate.of(1992, 05, 07));
        return user;
    }
}