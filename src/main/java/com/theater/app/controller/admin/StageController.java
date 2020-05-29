package com.theater.app.controller.admin;

import com.theater.app.domain.Stage;
import com.theater.app.service.StageService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
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


    @PostMapping("/updateStage")
    public String updateStagePost(@ModelAttribute("stage") Stage stage) {
//            if (result.hasErrors()) {
//                stage.setId(Long.valueOf(id));
//                return "admin/stage/stageList";
//            }

            stageService.save(stage);
//            model.addAttribute("users", stageService.findAll());
            return "redirect:stageList";
    }

    @GetMapping("/removeStage/{id}/")
    public String remove(@PathVariable("id") String id, Model model){
//        Stage stage = stageService.findById(Long.valueOf(id));
        stageService.remove(Long.valueOf(id));
//        model.addAttribute("stageList", stageService.findAll());
        return "admin/stage/stageList";
    }
}
