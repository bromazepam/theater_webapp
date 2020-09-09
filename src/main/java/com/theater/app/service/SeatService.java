package com.theater.app.service;

import com.theater.app.domain.Seat;
import com.theater.app.domain.Stage;

public interface SeatService {
    Iterable<Seat> save(Stage stage, int seatNum);
    void remove(String stageId);
    void update(String stageId, int newSeatNum);
}
