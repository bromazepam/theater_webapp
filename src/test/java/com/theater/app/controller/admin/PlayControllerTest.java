package com.theater.app.controller.admin;

import com.theater.app.domain.Play;
import com.theater.app.service.ImageService;
import com.theater.app.service.PlayService;
import com.theater.app.service.impl.PlayServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
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
        model.addAttribute("play", play);
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
    void playInfo() throws Exception {
        Play play = new Play();
        model.addAttribute("play", play);
        model.addAttribute("PHOTOYOUNEED", "string");
        given(playService.findById(any())).willReturn(play);

        mockMvc.perform(get("/playInfo/" + play.getId()+"/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("play", "PHOTOYOUNEED"))
                .andExpect(view().name("admin/play/playInfo"));

    }

    @Test
    void allPlays() throws Exception {
        given(playService.findAll()).willReturn(Lists.newArrayList(new Play(), new Play()));

        mockMvc.perform(get("/playList"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/play/playList"));

    }

    @Test
    void updatePlay() throws Exception {
        Play play = new Play();
        model.addAttribute("play", play);
        given(playService.findById(any())).willReturn(play);

        mockMvc.perform(get("/updatePlay/" + play.getId()+"/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("play"))
                .andExpect(view().name("admin/play/updatePlay"));
    }

    @Test
    void updatePlayPost() throws Exception {

    }

    @Test
    void remove() {
    }
}