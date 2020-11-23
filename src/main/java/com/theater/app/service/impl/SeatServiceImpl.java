package com.theater.app.service.impl;

import com.theater.app.domain.Seat;
import com.theater.app.domain.Stage;
import com.theater.app.repository.SeatRepository;
import com.theater.app.service.SeatService;
import com.theater.app.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final StageService stageService;

    @Transactional
    @Override
    public Iterable<Seat> save(Stage stage, int seatNum) {
        List<Seat> list = new ArrayList<>();
        for(int i = 0; i< seatNum;i++){
            Seat seat = new Seat();
            seat.setName(Integer.toString(i));
            seat.setStage(stage);
            list.add(seat);
        }
        stage.setSeats(list);
        stageService.save(stage);
        return seatRepository.saveAll(list);
    }

    @Transactional
    @Override
    public void remove(String stageId){
        Stage stage = stageService.findById(stageId);
        List<Seat> seats = (List<Seat>) seatRepository.findAll();
        List<Seat> result = seats.stream()
                .filter(seat -> seat.getStage().equals(stage))
                .collect(Collectors.toList());
        seatRepository.deleteAll(result);
    }

    @Transactional
    @Override
    public void update(String stageId, int newSeatNum) {
        Stage stage = stageService.findById(stageId);
        List<Seat> list = new ArrayList<>();
        List<Seat> seats = (List<Seat>) seatRepository.findAll();
        List<Seat> result = seats
                .stream()
                .filter(seat -> seat.getStage().equals(stage))
                .collect(Collectors.toList());
        int n = result.size();
        if(n<newSeatNum){
            for(int i = n+1; i<=newSeatNum;i++){
                Seat seat = new Seat();
                seat.setName(Integer.toString(i));
                seat.setStage(stage);
                list.add(seat);
            }
            stage.setSeats(list);
            seatRepository.saveAll(list);
            stageService.save(stage);
        }
        if(n>newSeatNum){
            List<Seat> collect = seats.stream()
                    .skip(newSeatNum)
                    .filter(seat -> seat.getStage().equals(stage))
                    .collect(Collectors.toList());
            seatRepository.deleteAll(collect);
            stage.setSeats(collect);
            stageService.save(stage);
        }
    }
}
