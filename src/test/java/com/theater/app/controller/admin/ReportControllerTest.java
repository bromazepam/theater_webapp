package com.theater.app.controller.admin;

import com.theater.app.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {ReportController.class})
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


}