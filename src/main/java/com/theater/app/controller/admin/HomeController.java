package com.theater.app.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/adminHome")
    public String adminHome() {
        return "admin/adminHome";
    }


}
