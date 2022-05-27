package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayTest {

    @Test
    void testGetPlayImage() throws UnsupportedEncodingException {
        assertEquals("", (new Play()).getPlayImage());
        assertEquals("QUFBQUFBQUE=", (new Play("42", "Dr", "JaneDoe", "", "", true,
                "The characteristics of someone or something", "AAAAAAAA".getBytes("UTF-8"))).getPlayImage());
        assertEquals("",
                (new Play("42", "Dr", "JaneDoe", "", "", true, "The characteristics of someone or something", new byte[]{}))
                        .getPlayImage());
    }

    @Test
    void testConstructor() {
        assertTrue((new Play()).isActive());
    }

}