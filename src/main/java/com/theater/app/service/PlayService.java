package com.theater.app.service;

import com.theater.app.domain.Play;

import java.util.List;
import java.util.Optional;

public interface PlayService {
    Play save(Play book);

    List<Play> findAll();

    Play findById(Long id);

    void removeOne(Long id);
}
