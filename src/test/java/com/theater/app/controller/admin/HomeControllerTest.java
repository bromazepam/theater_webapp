package com.theater.app.controller.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private HomeController controller;

    @DisplayName("test proper view name is returned ofr admin homepage")
    @Test
    void adminHome() {
        controller = new HomeController();
        assertEquals("admin/adminHome", controller.adminHome());
    }
}