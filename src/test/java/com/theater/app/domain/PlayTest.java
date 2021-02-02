package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class PlayTest {

    @Test
    void groupedAssertions() {
        Play play = new Play("1", "title", "author", "director", "category",
                true, "description", any(byte[].class));

        assertAll("test play",
                () -> assertEquals(play.getId(), "1", "play id failed"),
                () -> assertEquals(play.getTitle(), "title", "play title failed"),
                () -> assertEquals(play.getAuthor(), "author", "play author failed"),
                () -> assertEquals(play.getDirector(), "director", "play director failed"),
                () -> assertEquals(play.getCategory(), "category", "play category failed"),
                () -> assertTrue(play.isActive(), "play active failed"),
                () -> assertEquals(play.getDescription(), "description", "play description failed"),
                () -> assertEquals(play.getPlayImage(), "", "play image failed"));
    }

}