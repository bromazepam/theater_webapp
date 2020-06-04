package com.theater.app.service.impl;

import com.theater.app.domain.Seat;
import com.theater.app.domain.Stage;
import com.theater.app.repository.SeatRepository;
import com.theater.app.service.SeatService;
import com.theater.app.service.StageService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {
    private SeatRepository seatRepository;
    private StageService stageService;

    public SeatServiceImpl(SeatRepository seatRepository, StageService stageService) {
        this.seatRepository = seatRepository;
        this.stageService = stageService;
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

    @Override
    public void remove(long stageId){
        Stage stage = stageService.findById(Long.valueOf(stageId));
        List<Seat> seats = (List<Seat>) seatRepository.findAll();
        List<Seat> result = seats.stream()
                .filter(seat -> seat.getStage().equals(stage))
                .collect(Collectors.toList());
        seatRepository.deleteAll(result);
    }
}
