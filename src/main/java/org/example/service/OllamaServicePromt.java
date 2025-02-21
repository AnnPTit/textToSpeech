package org.example.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OllamaServicePromt {
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private final OkHttpClient client = new OkHttpClient();
    public String generateResponse(String prompt) {
        String jsonRequest = "{\"model\": \"gemma2:2b\", \"prompt\": \"" + prompt + "\"}";
        RequestBody body = RequestBody.create(jsonRequest, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(OLLAMA_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Ghép nội dung từ response thành một câu hoàn chỉnh
            StringBuilder fullResponse = new StringBuilder();
            if (response.body() != null) {
                String[] lines = response.body().string().split("\n");
                for (String line : lines) {
                    if (line.contains("\"response\"")) {
                        String part = line.split("\"response\":\"")[1].split("\"")[0];
                        if(!part.contains("\\n")){
                            fullResponse.append(part);
                        }
                    }
                }
            }
            return fullResponse.toString();
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
}
