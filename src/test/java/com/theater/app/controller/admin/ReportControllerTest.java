package com.theater.app.controller.admin;

import com.theater.app.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import javax.servlet.http.HttpServletRequest;

import java.io.FileNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @InjectMocks
    private ReportController controller;
    @Mock
    private ReportService service;

    @BeforeEach
    void setUp() {
        controller = new ReportController(service);
    }

    @DisplayName("test display name for report page")
    @Test
    void reports() {
        assertEquals("admin/reports", controller.reports());
    }

//    @Test
//    void testReports() throws Exception {
//
//
//    }
}