package com.theater.app.controller.admin;

import com.theater.app.domain.Play;
import com.theater.app.domain.Repertoire;
import com.theater.app.service.PlayService;
import com.theater.app.service.RepertoireService;
import com.theater.app.service.StageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO circle dependency
@Controller
public class RepertoireController {

    private final RepertoireService repertoireService;
    private final PlayService playService;
    private final StageService stageService;

    public RepertoireController(RepertoireService repertoireService,@Lazy PlayService playService,@Lazy StageService stageService) {
        this.repertoireService = repertoireService;
        this.playService = playService;
        this.stageService = stageService;
    }

    @GetMapping("/addRepertoire")
    public String addRepertoire(Model model) {
        model.addAttribute("repertoire", new Repertoire());
        model.addAttribute("plays", playService.findAll());
        model.addAttribute("stageList", stageService.findAll());
        return "admin/repertoire/addRepertoire";
    }

    @PostMapping("/addRepertoire")
    public String addStagePost(@ModelAttribute("repertoire") Repertoire repertoire) {
        repertoireService.save(repertoire);
        return "redirect:repertoire";
    }

    @RequestMapping("/repertoire")
    public String repertoireList(Model model) {
        model.addAttribute("repertoireList", repertoireService.findAll());
        model.addAttribute("plays", playService.findAll());
        model.addAttribute("stageList", stageService.findAll());
        return "admin/repertoire/repertoire";
    }

    @RequestMapping("/updateRepertoire/{id}/")
    public String updateRepertoire(@PathVariable String id, Model model) {
        model.addAttribute("repertoire", repertoireService.findById(Long.valueOf(id)));
        model.addAttribute("plays", playService.findAll());
        model.addAttribute("stageList", stageService.findAll());
        return "admin/repertoire/updateRepertoire";
    }


    @PostMapping("/updateRepertoire")
    public String updateStagePost(@ModelAttribute("repertoire") Repertoire repertoire) {
        repertoireService.save(repertoire);
        return "redirect:repertoire";
    }

    @GetMapping("/removeRepertoire/{id}/")
    public String remove(@PathVariable("id") String id, Model model) {
        repertoireService.deleteById(Long.valueOf(id));
        return "admin/repertoire/repertoire";
    }
}
