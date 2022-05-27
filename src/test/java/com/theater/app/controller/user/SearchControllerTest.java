package com.theater.app.controller.user;

import com.theater.app.domain.Play;
import com.theater.app.domain.Repertoire;
import com.theater.app.service.PlayService;
import com.theater.app.service.RepertoireService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ContextConfiguration(classes = {SearchController.class})
@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @InjectMocks
    SearchController searchController;
    @Mock
    PlayService playService;
    @Mock
    RepertoireService repertoireService;
    @Mock
    Model model;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @AfterEach
    void tearDown() {
        reset(playService, repertoireService);
    }

    @Test
    void searchByCategory() throws Exception {
        //given
        final String category = "drama";
        Play play = new Play();
        play.setId("1");
        play.setCategory(category);
        given(playService.findByCategory(category)).willReturn(Lists.newArrayList(play));

        //when
        mockMvc.perform(get("/searchByCategory")
                        .param("category", category))
                .andExpect(status().isOk())
                .andExpect(view().name("user/plays"));

        //then
        then(playService).should().findByCategory(anyString());
    }

    @DisplayName("diffblue")
    @Test
    void testSearchByDate2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/searchByDate");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("date", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.searchController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}