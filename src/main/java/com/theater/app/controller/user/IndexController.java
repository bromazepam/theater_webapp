package com.theater.app.controller.user;

import com.theater.app.domain.Play;
import com.theater.app.domain.Repertoire;
import com.theater.app.service.PlayService;
import com.theater.app.service.RepertoireService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    private final PlayService playService;
    private final RepertoireService repertoireService;

    public IndexController(PlayService playService, RepertoireService repertoireService) {
        this.playService = playService;
        this.repertoireService = repertoireService;
    }

    @RequestMapping("/")
    public String index(){
        return "redirect:/homescreen";
    }

    @RequestMapping("/login")
    public String login(){
        return "user/login";
    }

    @RequestMapping("/forgottenPass")
    public String forgetPassword(){
        return "user/forgotPassword";
    }

    @RequestMapping("/registration")
    public String registration(){
        return "user/registration";
    }


    @RequestMapping("/plays")
    public String plays(Model model) {

        List<Play> playList = playService.findAll();
        model.addAttribute("playList", playList);
        model.addAttribute("activeAll", true);

        return "user/plays";
    }

    @RequestMapping("/repertoireList")
    public String repertoire(Model model) {

        List<Repertoire> repertoireList = repertoireService.findAll();
        model.addAttribute("repertoireList", repertoireList);
        model.addAttribute("activeAll", true);

        if(repertoireList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "user/repertoireList";
        }

        return "user/repertoireList";
    }
}
