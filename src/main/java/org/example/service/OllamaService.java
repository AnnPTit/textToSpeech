package org.example.service;

import org.example.dto.OllamaResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class OllamaService {

    private final WebClient webClient;

    public OllamaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:11434").build();
    }


    public Flux<String> generateResponse(String prompt) {
        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"model\": \"gemma2:2b\", \"prompt\": \"" + prompt + "\"}")
                .retrieve()
                .bodyToFlux(OllamaResponse.class)  // Chuyển đổi JSON thành Object
                .map(OllamaResponse::getResponse); // Chỉ lấy phần response của JSON
    }

}
