package com.theater.app.service.impl;

import com.theater.app.domain.Seat;
import com.theater.app.domain.Stage;
import com.theater.app.repository.SeatRepository;
import com.theater.app.service.SeatService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    private SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Transactional
    @Override
    public Iterable<Seat> saveAll(Stage stage, int seatNum) {
        List<Seat> list = new ArrayList<>();
        for(int i = 0; i< seatNum;i++){
            Seat seat = new Seat();
            seat.setName(Integer.toString(i));
            seat.setStage(stage);
            list.add(seat);
        }
        return seatRepository.saveAll(list);
    }
}
