package com.theater.app.controller.user;

import com.theater.app.domain.Play;
import com.theater.app.domain.Repertoire;
import com.theater.app.service.PlayService;
import com.theater.app.service.RepertoireService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SearchController {

    private final PlayService playService;
    private final RepertoireService repertoireService;

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

    @RequestMapping("/searchByDate")
    public String searchByDate(@RequestParam("date") Date date, Model model){

        List<Repertoire> repertoireList = repertoireService.findByDate(date);

        if(repertoireList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "repertoireList";
        }
        model.addAttribute("repertoireList",repertoireList);
        return "repertoireList";
    }
}
