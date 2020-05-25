package com.theater.app.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlayController {

    @RequestMapping("/addPlay")
    public String addPlay() {
        return "admin/addPlay";
    }

    @RequestMapping("/playInfo")
    public String playInfo() {
        return "admin/playInfo";
    }

    @RequestMapping("/plays")
    public String allPlays() {
        return "admin/playList";
    }

    @RequestMapping("/updatePlay")
    public String updatePlay() {
        return "admin/updatePlay";
    }
}
