package com.theater.app.service.impl;

import com.theater.app.domain.Play;
import com.theater.app.repository.PlayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlayServiceImplTest {

    @InjectMocks
    PlayServiceImpl playService;

    @Mock
    PlayRepository playRepository;

    @Test
    void save() {
        //given
        Play play = mock(Play.class);

        //when
        playService.save(play);

        //then
        then(playRepository).should().save(play);
    }

    @Test
    void findAll() {
        //given
        List<Play> playList = new ArrayList<>();
        given(playRepository.findAll()).willReturn(playList);

        //when
        Collection<Play> returnedPlays = playService.findAll();

        //then
        then(playRepository).should().findAll();
        assertThat(returnedPlays).isNotNull();
    }

    @Test
    void findById() {
        //given
        Play play = new Play();
        given(playRepository.findById(any())).willReturn(java.util.Optional.of(play));

        //when
        Play returnedPlay = playService.findById(any());

        //then
        then(playRepository).should().findById(any());
        assertThat(returnedPlay).isNotNull();
    }

    @Test
    void removeById() {
        //given

        //when
        playService.removeById(any());

        //then
        then(playRepository).should().deleteById(any());
    }

    @Test
    void findByCategory() {
        //given
        List<Play> playList = new ArrayList<>();
        given(playRepository.findByCategory(any())).willReturn(playList);

        //when
        Collection<Play> returnedPlays = playService.findByCategory(any());

        //then
        then(playRepository).should().findByCategory(any());
        assertThat(returnedPlays).isNotNull();
    }
}