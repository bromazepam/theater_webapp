package com.theater.app.controller.admin;

import com.theater.app.domain.Stage;
import com.theater.app.service.SeatService;
import com.theater.app.service.StageService;
import org.assertj.core.util.Lists;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StageControllerTest {

    @InjectMocks
    StageController stageController;
    @Mock
    StageService stageService;
    @Mock
    SeatService seatService;
    @Mock
    Model model;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stageController).build();
    }

    @AfterEach
    void tearDown() {
        reset(stageService,seatService);
    }

    @Test
    void addStage() throws Exception {
        Stage stage = new Stage();
        model.addAttribute("stage", stage);
        mockMvc.perform(get("/addStage"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("stage"))
                .andExpect(view().name("admin/stage/addStage"));
    }

    @Test
    void addStagePost() throws Exception {
        mockMvc.perform(post("/addStage")
                .param("id","1")
                .param("name","sala")
                .param("capacity","200"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:stageList"));
    }

    @Test
    void allStages() throws Exception {
        given(stageService.findAll()).willReturn(Lists.newArrayList(new Stage(), new Stage()));

        mockMvc.perform(get("/stageList"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/stage/stageList"));
    }

    @Test
    void updateStage() throws Exception {
        Stage stage = new Stage();
        model.addAttribute("stage", stage);
        given(stageService.findById(any())).willReturn(stage);

        mockMvc.perform(get("/updateStage/" + stage.getId()+"/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("stage"))
                .andExpect(view().name("admin/stage/updateStage"));
    }

    @Test
    void updateStagePost() throws Exception {
        mockMvc.perform(post("/addStage")
                .param("name","sala")
                .param("capacity","200"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:stageList"));
    }

    @Test
    void remove() throws Exception {
        doNothing().when(stageService).remove(anyString());
        List<Stage> stageList = stageService.findAll();
        model.addAttribute("stageList", stageList);
        this.mockMvc.perform(get("/removeStage/{id}/","1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("stageList"))
                .andExpect(view().name("admin/stage/stageList"));
    }
}