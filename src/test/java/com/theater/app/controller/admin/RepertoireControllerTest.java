package com.theater.app.controller.admin;

import com.theater.app.domain.Repertoire;
import com.theater.app.service.PlayService;
import com.theater.app.service.RepertoireService;
import com.theater.app.service.StageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RepertoireControllerTest {

    @InjectMocks
    RepertoireController repertoireController;
    @Mock
    RepertoireService repertoireService;
    @Mock
    PlayService playService;
    @Mock
    StageService stageService;
    @Mock
    Model model;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(repertoireController).build();
    }

    @AfterEach
    void tearDown() {
        reset(repertoireService, playService, stageService);
    }

    @Test
    void addRepertoire() throws Exception {
        model.addAttribute("repertoire", new Repertoire());
        model.addAttribute("plays", playService.findAll());
        model.addAttribute("stageList",stageService.findAll());
        mockMvc.perform(get("/addRepertoire"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("repertoire","plays","stageList"))
                .andExpect(view().name("admin/repertoire/addRepertoire"));
    }

    @Test
    void repertoireList() throws Exception {
        model.addAttribute("repertoireList", repertoireService.findAll());
        mockMvc.perform(get("/repertoire"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("repertoireList"))
                .andExpect(view().name("admin/repertoire/repertoire"));
    }

    @Test
    void updateRepertoire() throws Exception {
        Repertoire repertoire = new Repertoire();
        model.addAttribute("repertoire", repertoire);
        model.addAttribute("plays", playService.findAll());
        model.addAttribute("stageList", stageService.findAll());
        given(repertoireService.findById(any())).willReturn(repertoire);

        mockMvc.perform(get("/updateRepertoire/{id}/","1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("repertoire","plays","stageList"))
                .andExpect(view().name("admin/repertoire/updateRepertoire"));
    }


    @Test
    void remove() throws Exception {
        doNothing().when(repertoireService).deleteById(anyString());
        List<Repertoire> repertoireList = repertoireService.findAll();
        model.addAttribute("repertoireList", repertoireList);
        this.mockMvc.perform(get("/removeRepertoire/{id}/","1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("repertoireList"))
                .andExpect(view().name("admin/repertoire/repertoire"));
    }
}