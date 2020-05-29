package com.theater.app.service;

import com.theater.app.domain.Play;

import java.util.List;

public interface PlayService {
    Play save(Play play);

    List<Play> findAll();

    Play findById(Long id);

    void removeOne(Long id);
}
