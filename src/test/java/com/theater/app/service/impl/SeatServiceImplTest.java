package com.theater.app.service.impl;

import com.theater.app.domain.Seat;
import com.theater.app.domain.Stage;
import com.theater.app.repository.SeatRepository;
import com.theater.app.service.StageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {SeatServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class SeatServiceImplTest {

    @MockBean
    private SeatRepository seatRepository;

    @Autowired
    private SeatServiceImpl seatServiceImpl;

    @MockBean
    private StageService stageService;

    @Test
    public void testSave() {
        //given
        List<Seat> list = new ArrayList<>();
        Stage stage = new Stage();
        stage.setCapacity(3);
        stage.setId("42");
        stage.setName("Name");
        Seat seat = new Seat("1", "1", false, stage);
        list.add(seat);
        stage.setSeats(list);

        //when
        stageService.save(stage);
        seatRepository.saveAll(list);

        //then
        then(seatRepository).should().saveAll(list);
    }

    @Test
    public void testRemove() {
        //given
        Stage stage = new Stage();
        stage.setCapacity(3);
        stage.setId("42");
        stage.setName("Name");
        stage.setSeats(new ArrayList<>());

        //when
        when(this.seatRepository.findAll()).thenReturn(new ArrayList<>());
        this.seatServiceImpl.remove("42");

        //then
        then(this.seatRepository).should().findAll();
        then(this.seatRepository).should().deleteAll(any());
    }

    @Test
    public void testUpdate() {
        //given
        Stage stage = new Stage();
        stage.setCapacity(3);
        stage.setId("42");
        stage.setName("Name");
        stage.setSeats(new ArrayList<>());

        //when
        when(this.stageService.findById(anyString())).thenReturn(stage);
        when(this.seatRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(this.seatRepository.findAll()).thenReturn(new ArrayList<>());
        this.seatServiceImpl.update("42", 10);

        //then
        then(this.seatRepository).should().findAll();
        then(this.seatRepository).should().saveAll(any());
        then(this.stageService).should().save(any());
        then(this.stageService).should().findById(anyString());
    }
}

