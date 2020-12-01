package com.theater.app.controller.admin;

import com.theater.app.domain.Stage;
import com.theater.app.service.SeatService;
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

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void addStagePost() {
    }

    @Test
    void allStages() {
    }

    @Test
    void updateStage() {
    }

    @Test
    void updateStagePost() {
    }

    @Test
    void remove() {
    }
}