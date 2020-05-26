package com.theater.app.service.impl;

import com.theater.app.domain.Play;
import com.theater.app.repository.PlayRepository;
import com.theater.app.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayServiceImpl implements PlayService {
    @Autowired
    private PlayRepository playRepository;

    public Play save(Play play){
        return playRepository.save(play);
    }

    @Override
    public List<Play> findAll() {
        return (List<Play>)playRepository.findAll();
    }

    @Override
    public Optional<Play> findOne(Long id) {
        return playRepository.findById(id);
    }

    @Override
    public void removeOne(Long id) {
        playRepository.deleteById(id);
    }

}
