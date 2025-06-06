package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.Sentence;
import org.example.domain.Word;
import org.example.service.SentenceService;
import org.example.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HomeController {

	private final WordService wordService;
	private final SentenceService sentenceService;

	@GetMapping("/topic")
	public String showTopic(Model model) {
		model.addAttribute("topics", wordService.showTopic());
		return "topic";
	}
//
//	@GetMapping("/gramar")
//	public String gramar(Model model) {
//		return "gramar-menu";
//	}


//	@GetMapping("/gramar-check")
//	public String gramarCheck(Model model) {
//		return "gramar-check";
//	}

	@GetMapping("/vocab")
	public String vocab(Model model) {
		return "vocab";
	}

	@GetMapping("/note-page")
	public String note(Model model) {
		model.addAttribute("lexicalCategory", wordService.lexicalCategory());
		List<Word> totalResults = wordService.getTotalElement("self-study");
		model.addAttribute("dataLts", totalResults);
		return "note";
	}

	@GetMapping("/translate")
	public String translate(Model model) {
		return "translate";
	}


    @GetMapping("/word-rearrange")
    public String wordRearRange() {
        return "word-rearrange";
    }

	@GetMapping("/word-rearrange-create")
	public String wordRearRangeCreate(Model model) {
		List<Sentence> list = sentenceService.getAllSentence();
		model.addAttribute("dataLts", list);
		return "word-rearrange-create";
	}

	@GetMapping("/")
	public String home() {
		return "index"; // Trả về file index.html trong thư mục templates
	}
}
