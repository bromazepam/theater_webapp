package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SeatTest {

    @Test
    void groupedAssertions() {
        Stage stage = mock(Stage.class);
        Seat seat = new Seat("1", "name", false, stage);

        assertAll("seat test",
                () -> assertEquals(seat.getId(), "1"),
                () -> assertEquals(seat.getName(), "name"),
                () -> assertFalse(seat.isReserved()),
                () -> assertEquals(seat.getStage(), stage));
    }

}