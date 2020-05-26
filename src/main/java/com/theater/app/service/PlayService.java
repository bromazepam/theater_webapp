package com.theater.app.service;

import com.theater.app.domain.Play;

import java.util.List;
import java.util.Optional;

public interface PlayService {
    Play save(Play book);

    List<Play> findAll();

    Optional<Play> findOne(Long id);

    void removeOne(Long id);
}
