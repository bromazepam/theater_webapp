package com.theater.app.service.impl;

import com.theater.app.domain.Play;
import com.theater.app.repository.PlayRepository;
import com.theater.app.service.PlayService;
import com.theater.app.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Play findById(String id) {
        Optional<Play> playOptional = playRepository.findById(id);

        if (!playOptional.isPresent()) {
            throw new NotFoundException("play not found, For ID value: " + id);
        }
        return playOptional.get();
    }

    @Override
    public void removeById(String id) {
        playRepository.deleteById(id);
    }

    @Override
    public List<Play> findByCategory(String category) {
        List<Play> playList = playRepository.findByCategory(category);
        List<Play> activePlayList = new ArrayList<>();

        for(Play play: playList){
            if(play.isActive()){
                activePlayList.add(play);
            }
        }
        return activePlayList;
    }
}
