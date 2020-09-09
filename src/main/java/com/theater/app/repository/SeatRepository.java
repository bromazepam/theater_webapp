package com.theater.app.repository;

import com.theater.app.domain.Seat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat, Long> {
    List<Seat> findByStageIdAndReservedFalse(String stageId);
}
