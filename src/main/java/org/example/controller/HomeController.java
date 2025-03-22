package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HomeController {

    private final WordService wordService;

    @GetMapping("/topic")
    public String showTopic(Model model) {
        model.addAttribute("topics", wordService.showTopic());
        return "topic";
    }

    @GetMapping("/gramar")
    public String gramar(Model model) {
//        model.addAttribute("topics", wordService.showTopic());
        return "gramar-menu";
    }


    @GetMapping("/gramar-check")
    public String gramarCheck(Model model) {
//        model.addAttribute("topics", wordService.showTopic());
        return "gramar-check";
    }

    @GetMapping("/vocab")
    public String vocab(Model model) {
//        model.addAttribute("topic", topic);
//        System.out.println("Hehe");
        return "vocab";
    }

    @GetMapping("/note")
    public String note(Model model) {
        model.addAttribute("lexicalCategory", wordService.lexicalCategory());
        model.addAttribute("dataLts", wordService.showTopicContent("self-study"));
        return "note";
    }

    @GetMapping("/translate")
    public String translate(Model model) {
//        model.addAttribute("lexicalCategory", wordService.lexicalCategory());
        return "translate";
    }



    @GetMapping("/")
    public String home() {
        return "index"; // Trả về file index.html trong thư mục templates
    }
}
