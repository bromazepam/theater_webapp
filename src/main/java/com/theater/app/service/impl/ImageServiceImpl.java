package com.theater.app.service.impl;

import com.theater.app.domain.Play;
import com.theater.app.repository.PlayRepository;
import com.theater.app.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final PlayRepository playRepository;

    public ImageServiceImpl(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    @Override
    public void saveImageFile(String id, MultipartFile file) {
        try {
            Play play = playRepository.findById(id).get();

            byte[] byteObjects = new byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            play.setPlayImage(byteObjects);

            playRepository.save(play);
        } catch (IOException e) {
            //todo handle better
            log.error("Error occurred", e);

            e.printStackTrace();
        }
    }
}
