package com.theater.app.service.impl;

import com.theater.app.domain.Stage;
import com.theater.app.exceptions.NotFoundException;
import com.theater.app.repository.StageRepository;
import com.theater.app.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StageServiceImpl implements StageService {

    private final StageRepository stageRepository;

    @Override
    public Stage save(Stage stage) {
        return stageRepository.save(stage);
    }

    @Override
    public List<Stage> findAll() {
        return (List<Stage>) stageRepository.findAll();
    }

    @Override
    public Stage findById(String id) {
        Optional<Stage> optionalStage = stageRepository.findById(id);

        if (!optionalStage.isPresent()) {
            throw new NotFoundException("Stage not found, For ID value: " + id);
        }
        return optionalStage.get();
    }

    @Override
    public void remove(String id) {
        stageRepository.deleteById(id);
    }
}
