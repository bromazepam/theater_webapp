package com.theater.app.service.impl;

import com.theater.app.domain.Play;
import com.theater.app.repository.PlayRepository;
import com.theater.app.service.PlayService;
import com.theater.app.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayServiceImpl implements PlayService {

    private PlayRepository playRepository;

    public PlayServiceImpl(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    public Play save(Play play){
        return playRepository.save(play);
    }

    @Override
    public List<Play> findAll() {
        return (List<Play>)playRepository.findAll();
    }

    @Override
    public Play findById(Long l) {
        Optional<Play> playOptional = playRepository.findById(l);

        if (!playOptional.isPresent()) {
            throw new NotFoundException("recipe not found, For ID value: " + l.toString());
        }
        return playOptional.get();
    }

    @Override
    public void removeOne(Long id) {
        playRepository.deleteById(id);
    }

}
