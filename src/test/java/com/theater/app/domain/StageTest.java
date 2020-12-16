package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class StageTest {

    @Test
    void groupedAssertions() {
        ArrayList arrayList = mock(ArrayList.class);
        Stage stage = new Stage("1", "stage", 100, arrayList);

        assertAll("test props set",
                () -> assertEquals(stage.getId(), "1", "stage id failed"),
                () -> assertEquals(stage.getName(), "stage", "stage name failed"),
                () -> assertEquals(stage.getCapacity(), 100, "stage capacity failed"),
                () -> assertEquals(stage.getSeats(), arrayList, "stage seats failed"));
    }
}