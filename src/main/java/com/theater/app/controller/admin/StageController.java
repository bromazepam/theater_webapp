package com.theater.app.controller.admin;

import com.theater.app.domain.Stage;
import com.theater.app.service.SeatService;
import com.theater.app.service.StageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StageController {

    private final StageService stageService;
    private final SeatService seatService;

    @GetMapping("/addStage")
    public String addStage(Model model) {
        model.addAttribute("stage", new Stage());
        return "admin/stage/addStage";
    }

    @PostMapping("/addStage")
    public String addStagePost(@ModelAttribute("stage") Stage stage) {
        stageService.save(stage);
        seatService.save(stage, stage.getCapacity());
        return "redirect:stageList";
    }

    @RequestMapping("/stageList")
    public String allStages(Model model) {
        model.addAttribute("stageList", stageService.findAll());
        return "admin/stage/stageList";
    }

    @RequestMapping("/updateStage/{id}/")
    public String updateStage(@PathVariable String id, Model model) {
        model.addAttribute("stage", stageService.findById(id));
        return "admin/stage/updateStage";
    }


    @PostMapping("/updateStage")
    public String updateStagePost(@ModelAttribute("stage") Stage stage) {
        stageService.save(stage);
        seatService.update(stage.getId(),stage.getCapacity());
        return "redirect:stageList";
    }

    @GetMapping("/removeStage/{id}/")
    public String remove(@PathVariable("id") String id, Model model) {
        seatService.remove(id);
        stageService.remove(id);
        model.addAttribute("stageList", stageService.findAll());
        return "admin/stage/stageList";
    }
}
