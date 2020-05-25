package com.theater.app.service;

import com.theater.app.domain.Play;

import java.util.List;

public interface PlayService {
    Play save(Play book);

    List<Play> findAll();

    Play findOne(Long id);

    void removeOne(Long id);
}
