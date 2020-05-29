package com.theater.app.controller.admin;

import com.theater.app.domain.Play;
import com.theater.app.service.PlayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
public class PlayController {

    private PlayService playService;

    public PlayController(PlayService playService) {
        this.playService = playService;
    }

    @GetMapping("/add")
    public String addPlay(Model model) {
        Play play = new Play();
        model.addAttribute("play", play);
        return "admin/play/addPlay";
    }

    @PostMapping("/add")
    public String addPlayPost(@ModelAttribute("play") Play play, HttpServletRequest request) {
        playService.save(play);

        MultipartFile playImage = play.getPlayImage();

        try {
            byte[] bytes = playImage.getBytes();
            String name = play.getId() + ".png";
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(new File("src/main/resources/static/image/" + name)));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:playList";
    }

    @RequestMapping("/playInfo/{id}/")
    public String playInfo(@PathVariable String id, Model model) {

        model.addAttribute("play", playService.findById(Long.valueOf(id)));

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

        model.addAttribute("play", playService.findById(Long.valueOf(id)));

        return "admin/play/updatePlay";
    }

    @PostMapping("/updatePlay/{id}/")
    public String updatePlayPost(@ModelAttribute("play") Play play) {
        playService.save(play);

        MultipartFile playImage = play.getPlayImage();

        if (!playImage.isEmpty()) {
            try {
                byte[] bytes = playImage.getBytes();
                String name = play.getId() + ".png";

                Files.delete(Paths.get("src/main/resources/static/image/" + name));

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("src/main/resources/static/image/" + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:playInfo/{id}/" + play.getId();
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute("id") String id, Model model) {
        playService.removeOne(Long.parseLong(id.substring(8)));
        List<Play> playList = playService.findAll();
        model.addAttribute("playList", playList);

        return "redirect:admin/play/playList";
    }
}
