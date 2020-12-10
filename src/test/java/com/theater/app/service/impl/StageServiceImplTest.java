package com.theater.app.service.impl;

import com.theater.app.domain.Stage;
import com.theater.app.repository.StageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StageServiceImplTest {

    @Mock
    StageRepository stageRepository;
    @InjectMocks
    StageServiceImpl service;

    @Test
    void save() {
        Stage stage = new Stage();
        service.save(stage);
        verify(stageRepository).save(stage);
    }

    @Test
    void findAll() {
        //given
        List<Stage> stageList = new ArrayList<>();
        given(stageRepository.findAll()).willReturn(stageList);

        //when
        Collection<Stage> returnedStage = service.findAll();

        //then
        then(stageRepository).should().findAll();
        assertThat(returnedStage).isNotNull();
    }

    @Test
    void findById() {
        //given
        Stage stage = new Stage();
        given(stageRepository.findById(any())).willReturn(java.util.Optional.of(stage));

        //when
        Stage returnedStage = service.findById(any());

        //then
        then(stageRepository).should().findById(any());
        assertThat(returnedStage).isNotNull();

    }

    @Test
    void remove() {
        service.remove(any());
        verify(stageRepository).deleteById(any());
    }
}