package com.theater.app.controller.admin;

import com.theater.app.domain.Play;
import com.theater.app.service.ImageService;
import com.theater.app.service.PlayService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
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

    private MockMvc mockMvc;

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

    //todo
    @Disabled
    @Test
    void addPlayPost() throws Exception {
        String fileName = "test.txt";
        MockMultipartFile imagefile = new MockMultipartFile("imagefile", fileName,
                "text/plain", "test data".getBytes());
        mockMvc.perform(multipart("/add")
                .file(imagefile)
                .param("title", "Once upon a time")
                .param("author", "Jimmy")
                .param("director", "Buffet")
                .param("category", "drama")
                .param("boolean", "true")
                .param("description", "some plot"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:playList"));

    }

    @Test
    void playInfo() throws Exception {
        Play play = new Play();
        given(playService.findById(any())).willReturn(play);
        String PHOTOYOUNEED = play.getPlayImage();
        model.addAttribute("play", play);
        model.addAttribute("PHOTOYOUNEED", PHOTOYOUNEED);

        mockMvc.perform(get("/playInfo/" + play.getId() + "/"))
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

        mockMvc.perform(get("/updatePlay/" + play.getId() + "/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("play"))
                .andExpect(view().name("admin/play/updatePlay"));
    }

    //todo
    @Disabled
    @Test
    void updatePlayPost() {

    }

    @Test
    void remove() throws Exception {
        doNothing().when(playService).removeById(anyString());
//        given(playService.findAll()).willReturn(Lists.newArrayList(new Play(), new Play()));
        List<Play> playList = playService.findAll();
        model.addAttribute("playList", playList);
        this.mockMvc.perform(post("/remove"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("playList"))
                .andExpect(view().name("redirect:admin/play/playList"));
    }
}