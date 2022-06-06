package com.theater.app.controller.admin;

import com.theater.app.domain.Play;
import com.theater.app.repository.PlayRepository;
import com.theater.app.service.ImageService;
import com.theater.app.service.PlayService;
import com.theater.app.service.impl.ImageServiceImpl;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {PlayController.class})
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
    public void testAddPlay() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/add");
        MockMvcBuilders.standaloneSetup(this.playController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("play"))
                .andExpect(MockMvcResultMatchers.view().name("admin/play/addPlay"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("admin/play/addPlay"));
    }

    @Test
    void updatePlayGet() throws Exception {
        Play play = new Play();
        model.addAttribute("play", play);
        given(playService.findById(any())).willReturn(play);

        mockMvc.perform(get("/updatePlay/" + play.getId() + "/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("play"))
                .andExpect(view().name("admin/play/updatePlay"));
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

    @Test
    public void testUpdatePlayPost() {
        Play play2 = new Play();
        play2.setId("42");
        play2.setDirector("Director");
        play2.setCategory("Category");
        play2.setAuthor("JaneDoe");
        play2.setTitle("Dr");
        play2.setActive(true);
        play2.setDescription("The characteristics of someone or something");
        play2.setPlayImage("AAAAAAAA".getBytes());
        Optional<Play> ofResult = Optional.<Play>of(play2);
        PlayRepository playRepository1 = mock(PlayRepository.class);
        when(playRepository1.save((Play) org.mockito.Mockito.any())).thenReturn(play2);
        when(playRepository1.findById(org.mockito.Mockito.anyString())).thenReturn(ofResult);
        PlayController playController = new PlayController(playService, new ImageServiceImpl(playRepository1));
        Play play3 = mock(Play.class);
        when(play3.getId()).thenReturn("foo");
        assertEquals("redirect:playInfo/{id}/foo",
                playController.updatePlayPost(play3, new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes())));
        verify(play3, times(2)).getId();
        verify(playRepository1).save((Play) org.mockito.Mockito.any());
        verify(playRepository1).findById(org.mockito.Mockito.anyString());
        verify(playRepository1).save((Play) org.mockito.Mockito.any());
    }
}