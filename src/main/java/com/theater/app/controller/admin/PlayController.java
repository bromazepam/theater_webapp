package com.theater.app.controller.admin;

import com.theater.app.domain.Play;
import com.theater.app.service.ImageService;
import com.theater.app.service.PlayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PlayController {

    private final PlayService playService;
    private final ImageService imageService;

    @GetMapping("/add")
    public String addPlay(Model model) {
        Play play = new Play();
        model.addAttribute("play", play);
        System.out.println(play.getId());
        return "admin/play/addPlay";
    }

    @PostMapping("/add")
    public String addPlayPost(@ModelAttribute("play") Play play, @RequestParam("imagefile") MultipartFile file) {
        playService.save(play);
        imageService.saveImageFile(playService.findById(play.getId()).getId(),file);

        return "redirect:playList";
    }

    @RequestMapping("/playInfo/{id}/")
    public String playInfo(@PathVariable String id, Model model) {
        Play play =  playService.findById(id);
        String photoencodeBase64 = play.getPlayImage();
        model.addAttribute("PHOTOYOUNEED", photoencodeBase64);
        model.addAttribute("play", play);
        System.out.println(photoencodeBase64);
        return "admin/play/playInfo";

    }

    @RequestMapping("/playList")
    public String allPlays(Model model) {
        List<Play> playList = playService.findAll();
        model.addAttribute("playList", playList);
        return "admin/play/playList";
    }

    @GetMapping("/updatePlay/{id}/")
    public String updatePlay(@PathVariable String id, Model model) {

        model.addAttribute("play", playService.findById(id));

        return "admin/play/updatePlay";
    }

    @PostMapping("/updatePlay/{id}/")
    public String updatePlayPost(@ModelAttribute("play") Play play, @RequestParam("imagefile") MultipartFile file) {
        playService.save(play);
        imageService.saveImageFile(play.getId(),file);

        return "redirect:playInfo/{id}/" + play.getId();
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute("id") String id, Model model) {
        playService.removeById(id);
        List<Play> playList = playService.findAll();
        model.addAttribute("playList", playList);

        return "redirect:admin/play/playList";
    }
}
