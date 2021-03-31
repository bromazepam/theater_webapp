package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class RepertoireTest {

    @Test
    void groupedAssertions() {
        Stage stage = mock(Stage.class);
        Play play = mock(Play.class);
        Date date = mock(Date.class);
        Repertoire repertoire = new Repertoire("1", stage, play, true, date,
                date, "12/02/2020", date, 20, 23);

        assertAll("test play",
                () -> assertEquals(repertoire.getId(), "1", "repertoire id failed"),
                () -> assertEquals(repertoire.getStage(), stage, "repertoire stage failed"),
                () -> assertEquals(repertoire.getPlay(), play, "repertoire play failed"),
                () -> assertTrue(repertoire.isStatus(), "repertoire status failed"),
                () -> assertEquals(repertoire.getTimestamp(), date, "repertoire timestamp failed"),
                () -> assertEquals(repertoire.getProjectionDate(), date, "repertoire projection date failed"),
                () -> assertEquals(repertoire.getProjectionTime(),
                        "12/02/2020", "repertoire projection time failed"),
                () -> assertEquals(repertoire.getProjection_datetime(), date, "repertoire dateTime failed"),
                () -> assertEquals(repertoire.getPrice(), 20, "repertoire price faield"),
                () -> assertEquals(repertoire.getAvailableSeats(), 23, "repertoire available seats failed"));
    }
}

