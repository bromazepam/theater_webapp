package com.theater.app.service;

import com.theater.app.domain.Stage;

import java.util.List;

public interface StageService {
    Stage save(Stage stage);

    List<Stage> findAll();

    Stage findById(String id);

    void remove(String id);
}
