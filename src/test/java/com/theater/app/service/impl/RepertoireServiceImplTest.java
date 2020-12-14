package com.theater.app.service.impl;

import com.theater.app.domain.Repertoire;
import com.theater.app.domain.Seat;
import com.theater.app.repository.RepertoireRepository;
import com.theater.app.repository.SeatRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RepertoireServiceImplTest {

    @Mock
    RepertoireRepository repertoireRepository;
    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    RepertoireServiceImpl repertoireService;

    @Test
    void save() {
        Repertoire repertoire = new Repertoire();
        repertoireService.save(repertoire);
        verify(repertoireRepository).save(repertoire);
    }

    @Test
    void findAll() {
        //given
        List<Repertoire> repertoireList = new ArrayList<>();
        given(repertoireRepository.findAll()).willReturn(repertoireList);

        //when
        Collection<Repertoire> returnedRepertoire = repertoireService.findAll();

        //then
        then(repertoireRepository).should().findAll();
        assertThat(returnedRepertoire).isNotNull();
    }

    @Test
    void findById() {
        //given
        Repertoire repertoire = new Repertoire();
        given(repertoireRepository.findById(any())).willReturn(java.util.Optional.of(repertoire));

        //when
        Repertoire returnedRepertoire = repertoireService.findById(any());

        //then
        then(repertoireRepository).should().findById(any());
        assertThat(returnedRepertoire).isNotNull();
    }

    @Disabled
    @Test
    void findByDate() {
        //given
        List<Repertoire> repertoireList = new ArrayList<>();
        given(repertoireRepository.findByProjectionDate(any(Date.class))).willReturn(repertoireList);

        //when
        Collection<Repertoire> returnedRepertoire = repertoireService.findByDate(any(Date.class));

        //then
        then(repertoireRepository).should().findByProjectionDate(any(Date.class));
        assertThat(returnedRepertoire).isNotNull();
    }

    @Test
    void deleteById() {
        repertoireService.deleteById(any());
        verify(repertoireRepository).deleteById(any());
    }

    @Test
    void availableSeats() {
        //given
        List<Seat> seatList = new ArrayList<>();
        given(seatRepository.findByStageIdAndReservedFalse(any())).willReturn(seatList);

        //when
        int seats = repertoireService.availableSeats(any());

        //then
        then(seatRepository).should().findByStageIdAndReservedFalse(any());
        assertThat(seats).isNotNull();
    }

    @Disabled
    @Test
    void findByPresentOrFutureDate() {
        //given
        List<Repertoire> repertoireList = new ArrayList<>();
        given(repertoireRepository.findByProjectionDateGreaterThanEqual(any())).willReturn(repertoireList);

        //when
        Collection<Repertoire> returnedRepertoire = repertoireService.findByPresentOrFutureDate(any(Date.class));

        //then
        then(repertoireRepository).should().findByProjectionDateGreaterThanEqual(any());
        assertThat(returnedRepertoire).isNotNull();
    }
}