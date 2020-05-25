package com.theater.app.controller.admin;

import com.theater.app.domain.Play;
import com.theater.app.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/play")
public class PlayController {

    @Autowired
    private PlayService playService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPlay(Model model) {
        Play play = new Play();
        model.addAttribute("play", play);
        return "admin/addPlay";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
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

    @RequestMapping("/playInfo")
    public String playInfo(@RequestParam("id") Long id, Model model) {
        Play play = playService.findOne(id);
        model.addAttribute("play", play);

        return "admin/playInfo";
    }

    @RequestMapping("/playList")
    public String allPlays(Model model) {
        List<Play> playList = playService.findAll();
        model.addAttribute("playList", playList);
        return "admin/playList";
    }

    @RequestMapping("/updatePlay")
    public String updatePlay(@RequestParam("id") Long id, Model model) {
        Play play = playService.findOne(id);
        model.addAttribute("play", play);

        return "admin/updatePlay";
    }

    @RequestMapping(value="/updatePlay", method=RequestMethod.POST)
    public String updateBookPost(@ModelAttribute("play") Play play, HttpServletRequest request) {
        playService.save(play);

        MultipartFile playImage = play.getPlayImage();

        if(!playImage.isEmpty()) {
            try {
                byte[] bytes = playImage.getBytes();
                String name = play.getId() + ".png";

                Files.delete(Paths.get("src/main/resources/static/image/"+name));

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("src/main/resources/static/image/" + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/play/playInfo?id="+play.getId();
    }
}
