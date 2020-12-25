package com.theater.app.service.impl;

import com.theater.app.domain.Play;
import com.theater.app.repository.PlayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ImageServiceImplTest {

    @InjectMocks
    ImageServiceImpl imageService;
    @Mock
    PlayRepository playRepository;

    @Test
    void saveImageFile() {
        Play play = new Play();
        String fileName = "test.txt";
        MultipartFile multipartFile = new MockMultipartFile("user-file", fileName,
                "text/plain", "test data".getBytes());
        given(playRepository.findById(any())).willReturn(java.util.Optional.of(play));
        byte[] bytes = new byte[anyByte()];

        //when
        play.setPlayImage(bytes);
        imageService.saveImageFile(play.getId(), multipartFile);

        //then
        then(playRepository).should().save(play);


    }
}