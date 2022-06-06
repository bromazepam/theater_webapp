package com.theater.app.repository;

import com.theater.app.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteById("1");
    }

    @Test
    void findByUsername() {
        //given
        User user = User.builder()
                .username("testName")
                .id("1").build();
        userRepository.save(user);
        //when
        User expected = userRepository.findByUsername("testName");
        //then
        assertThat(expected.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void findByEmail() {
        //given
        User user = User.builder()
                .email("test@mail.com")
                .id("1").build();
        userRepository.save(user);
        //when
        User expected = userRepository.findByEmail("test@mail.com");
        //then
        assertThat(expected.getEmail()).isEqualTo(user.getEmail());
    }
}