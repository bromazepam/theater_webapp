package com.theater.app.controller.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    @DisplayName("test proper view name is returned ofr admin homepage")
    @Test
    void adminHome() {
        HomeController controller = new HomeController();
        assertEquals("admin/adminHome", controller.adminHome());
    }
}