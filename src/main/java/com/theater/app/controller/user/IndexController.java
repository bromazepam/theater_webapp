package com.theater.app.controller.user;

import com.theater.app.domain.Play;
import com.theater.app.service.PlayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class IndexController {

    private final PlayService playService;

    public IndexController(PlayService playService) {
        this.playService = playService;
    }

    @RequestMapping("/plays")
    public String plays(Model model) {

        List<Play> playList = playService.findAll();
        model.addAttribute("playList", playList);
        model.addAttribute("activeAll", true);

        return "user/plays";
    }
}
