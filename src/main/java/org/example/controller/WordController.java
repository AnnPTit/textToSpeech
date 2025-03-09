package org.example.controller;

import org.example.service.OllamaService;
import org.example.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin // Cho phép frontend gọi API từ trình duyệt
public class WordController {
    @Autowired
    private WordService wordService;

    @Autowired
    OllamaService ollamaService;


    @GetMapping("/vocab/{topic}")
    public List<String> getVocab(@PathVariable String topic) {
        return wordService.showTopicContent(topic);
    }

    @PostMapping(value = "/gramar", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> checkGramar(@RequestBody Map<String, String> request) {
        String sentence = request.get("sentence");
        System.out.println("Câu nhận được: " + sentence);

        String prompt = "Can you check the grammar of this sentence for me? : " + sentence + ". Answer in Vietnamese";


        return ollamaService.generateResponse(prompt)
                .collectList() // Thu thập tất cả phản hồi thành một danh sách
                .map(responses -> String.join(" ", responses)) // Ghép tất cả phần tử thành một chuỗi
                .doOnNext(response -> System.out.println("Phản hồi AI: " + response));
    }

}
