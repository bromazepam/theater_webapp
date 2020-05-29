package com.theater.app.controller.admin;

import com.theater.app.domain.Repertoire;
import com.theater.app.service.RepertoireService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RepertoireController {
    private RepertoireService repertoireService;

    public RepertoireController(RepertoireService repertoireService) {
        this.repertoireService = repertoireService;
    }

    @GetMapping("/addRepertoire")
    public String addRepertoire(Model model){
        model.addAttribute("stage", new Repertoire());
        return "admin/repertoire/addRepertoire";
    }

    @PostMapping("/addRepertoire")
    public String addStagePost(@ModelAttribute("repertoire") Repertoire repertoire){
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
        model.addAttribute("repertoire",  repertoireService.findById(Long.valueOf(id)));
        return "admin/repertoire/updateRepertoire";
    }


    @PostMapping("/updateRepertoire")
    public String updateStagePost(@ModelAttribute("repertoire") Repertoire repertoire) {
        repertoireService.save(repertoire);
        return "redirect:repertoire";
    }

    @GetMapping("/removeRepertoire/{id}/")
    public String remove(@PathVariable("id") String id, Model model){
        repertoireService.deleteById(Long.valueOf(id));
        return "admin/repertoire/repertoire";
    }
}
