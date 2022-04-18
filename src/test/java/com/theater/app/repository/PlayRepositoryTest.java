package com.theater.app.repository;

import com.theater.app.domain.Play;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class PlayRepositoryTest {

    @Autowired
    private PlayRepository playRepository;

    @AfterEach
    void tearDown() {
        playRepository.deleteById("1");
    }

    @Test
    void findByCategory() {
        //given
        Play play = Play.builder()
                .category("testCategory")
                .id("1").build();
        playRepository.save(play);
        //when
        List<Play> expected = playRepository.findByCategory("testCategory");
        //then
        assertThat(expected.get(0).getCategory()).isEqualTo(play.getCategory());
    }
}