package com.theater.app.controller.user;

import com.theater.app.domain.Play;
import com.theater.app.service.PlayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final PlayService playService;

    public SearchController(PlayService playService) {
        this.playService = playService;
    }

    @RequestMapping("/searchByCategory")
    public String searchByCategory(@RequestParam("category") String category, Model model){
        String classActiveCategory = "active" + category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("$","");
        model.addAttribute(classActiveCategory, true);

        List<Play> playList = playService.findByCategory(category);

        if(playList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "user/plays";
        }
        model.addAttribute("playList", playList);
        return "user/plays";
    }
}
