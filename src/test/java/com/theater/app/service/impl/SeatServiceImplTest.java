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
        List<Seat> list = new ArrayList<>();
        Stage stage = new Stage();
        stage.setCapacity(3);
        stage.setId("42");
        stage.setName("Name");
        Seat seat = new Seat("1", "1", false, stage);
        list.add(seat);
        stage.setSeats(list);

        stageService.save(stage);
        seatRepository.saveAll(list);

        verify(seatRepository).saveAll(list);
    }

    @Test
    public void testRemove() {
        Stage stage = new Stage();
        stage.setCapacity(3);
        stage.setId("42");
        stage.setName("Name");
        stage.setSeats(new ArrayList<Seat>());
        when(this.seatRepository.findAll()).thenReturn(new ArrayList<Seat>());
        this.seatServiceImpl.remove("42");
        verify(this.seatRepository).findAll();
        verify(this.seatRepository).deleteAll((Iterable<? extends Seat>) any());
    }

    @Test
    public void testUpdate() {
        Stage stage = new Stage();
        stage.setCapacity(3);
        stage.setId("42");
        stage.setName("Name");
        stage.setSeats(new ArrayList<Seat>());

        when(this.stageService.findById(anyString())).thenReturn(stage);
        when(this.seatRepository.saveAll((Iterable<Seat>) any())).thenReturn(new ArrayList<Seat>());
        when(this.seatRepository.findAll()).thenReturn(new ArrayList<Seat>());
        this.seatServiceImpl.update("42", 10);
        verify(this.seatRepository).findAll();
        verify(this.seatRepository).saveAll((Iterable<Seat>) any());
        verify(this.stageService).save((Stage) any());
        verify(this.stageService).findById(anyString());
    }
}

