package com.theater.app.controller.admin;

import com.theater.app.domain.Stage;
import com.theater.app.service.StageService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Component
public class StageController {

    private StageService stageService;

    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping("/addStage")
    public String addStage(Model model){
        model.addAttribute("stage", new Stage());
        return "admin/stage/addStage";
    }

    @PostMapping("/addStage")
    public String addStagePost(@ModelAttribute("stage") Stage stage){
        stageService.save(stage);
        return "redirect:stageList";
    }

    @RequestMapping("/stageList")
    public String allStages(Model model) {
        model.addAttribute("stageList", stageService.findAll());
        return "admin/stage/stageList";
    }

    @RequestMapping("/updateStage/{id}/")
    public String updateStage(@PathVariable String id, Model model) {
        model.addAttribute("stage",  stageService.findById(Long.valueOf(id)));
        return "admin/stage/updateStage";
    }

    @PostMapping("/updateStage/{id}/")
    public String updateStagePost(@ModelAttribute("stage") Stage stage) {
        stageService.save(stage);
        return "redirect:/stageList";
    }

    @PostMapping("/removeStage")
    public String remove(@ModelAttribute("id") String id, Model model){
        stageService.remove(Long.parseLong(id.substring(8)));
        model.addAttribute("stageList", stageService.findAll());
        return "redirect:admin/stage/stageList";
    }
}
