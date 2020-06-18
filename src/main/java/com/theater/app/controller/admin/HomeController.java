package com.theater.app.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/adminHome")
    public String adminHome() {
        return "admin/adminHome";
    }

    @RequestMapping("/reports")
    public String reports(){
        return "admin/reports";
    }
}
