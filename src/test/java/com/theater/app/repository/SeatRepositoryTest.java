package com.theater.app.repository;

import com.theater.app.domain.Seat;
import com.theater.app.domain.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class SeatRepositoryTest {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private StageRepository stageRepository;

    @AfterEach
    void tearDown() {
        stageRepository.deleteById("1");
        stageRepository.deleteById("1");
    }

    @Test
    void findByStageIdAndReservedFalse() {
        //given
        Stage stage = Stage.builder()
                .id("1").build();
        stageRepository.save(stage);
        Seat seat = Seat.builder()
                .name("test")
                .id("1")
                .reserved(false)
                .stage(stage).build();
        seatRepository.save(seat);
        //when
        List<Seat> expected = seatRepository.findByStageIdAndReservedFalse("1");
        //then
        assertThat(expected).hasSize(1);
        assertThat(expected.get(0).isReserved()).isEqualTo(seat.isReserved());

    }
}