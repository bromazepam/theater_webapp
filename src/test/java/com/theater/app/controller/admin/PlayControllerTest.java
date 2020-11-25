package com.theater.app.controller.admin;

import com.theater.app.domain.Play;
import com.theater.app.service.ImageService;
import com.theater.app.service.PlayService;
import com.theater.app.service.impl.PlayServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PlayControllerTest {

    @InjectMocks
    PlayController playController;

    @Mock
    PlayService playService;
    @Mock
    ImageService imageService;
    @Mock
    Model model;

     MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(playController).build();
    }

    @AfterEach
    void tearDown() {
        reset(playService, imageService);
    }

    @Test
    void addPlay() throws Exception {
        Play play = new Play();
        model.addAttribute("play",play);
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("play"))
                .andExpect(view().name("admin/play/addPlay"));
    }

    @Test
    void addPlayPost() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("playImage", "some bytes".getBytes());
        mockMvc.perform(multipart("/add")
                .file(firstFile)
                .param("title", "Once upon a time")
                .param("author", "Jimmy")
                .param("director", "Buffet")
                .param("category", "drama")
                .param("boolean", "true")
                .param("description", "some plot"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:playList"));

    }

    @Test
    void playInfo() {
    }

    @Test
    void allPlays() {
    }

    @Test
    void updatePlay() {
    }

    @Test
    void updatePlayPost() {
    }

    @Test
    void remove() {
    }
}