package com.theater.app.controller.admin;

import com.theater.app.domain.Repertoire;
import com.theater.app.service.PlayService;
import com.theater.app.service.RepertoireService;
import com.theater.app.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class RepertoireController {

    private final RepertoireService repertoireService;
    private final PlayService playService;
    private final StageService stageService;

    @GetMapping("/addRepertoire")
    public String addRepertoire(Model model) {
        model.addAttribute("repertoire", new Repertoire());
        model.addAttribute("plays", playService.findAll());
        model.addAttribute("stageList", stageService.findAll());
        return "admin/repertoire/addRepertoire";
    }

    @PostMapping("/addRepertoire")
    public String addRepertoirePost(@ModelAttribute("repertoire") Repertoire repertoire) {
        repertoire.setAvailableSeats(repertoireService.availableSeats(repertoire.getStage().getId()));
        repertoire.setStage(stageService.findById(repertoire.getStage().getId()));
        repertoireService.save(repertoire);
        return "redirect:repertoire";
    }

    @RequestMapping("/repertoire")
    public String repertoireList(Model model) {
        model.addAttribute("repertoireList", repertoireService.findAll());
        return "admin/repertoire/repertoire";
    }

    @RequestMapping("/updateRepertoire/{id}/")
    public String updateRepertoire(@PathVariable String id, Model model) {
        model.addAttribute("repertoire", repertoireService.findById(id));
        model.addAttribute("plays", playService.findAll());
        model.addAttribute("stageList", stageService.findAll());
        return "admin/repertoire/updateRepertoire";
    }


    @PostMapping("/updateRepertoire")
    public String updateRepertoirePost(@ModelAttribute("repertoire") Repertoire repertoire) {
        repertoire.setAvailableSeats(repertoireService.availableSeats(repertoire.getStage().getId()));
        repertoire.setStage(stageService.findById(repertoire.getStage().getId()));
        repertoireService.save(repertoire);
        return "redirect:repertoire";
    }

    @GetMapping("/removeRepertoire/{id}/")
    public String remove(@PathVariable("id") String id, Model model) {
        repertoireService.deleteById(id);
        model.addAttribute("repertoireList", repertoireService.findAll());
        return "admin/repertoire/repertoire";
    }
}
