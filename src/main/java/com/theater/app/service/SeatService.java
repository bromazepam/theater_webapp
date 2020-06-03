package com.theater.app.service;

import com.theater.app.domain.Seat;
import com.theater.app.domain.Stage;

public interface SeatService {
    Iterable<Seat> saveAll(Stage stage, int seatNum);
}
