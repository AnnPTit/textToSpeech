package org.example.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class OllamaServicePromt {
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private final OkHttpClient client = new OkHttpClient();
    private final StringBuilder fullResponse = new StringBuilder(); // Biến lưu phản hồi trên cùng một dòng


    public String generateResponse(String prompt) {
        String jsonRequest = "{\"model\": \"deepseek-r1:8b\", \"prompt\": \"" + prompt + "\", \"stream\": true}";

        RequestBody body = RequestBody.create(jsonRequest, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(OLLAMA_URL)
                .post(body)
                .build();

        System.out.println("Start call...");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    System.err.println("Unexpected response: " + response);
                    return;
                }

                System.out.println("Receiving response...");

                assert response.body() != null;
                try (var responseBody = response.body().source()) {
                    while (!responseBody.exhausted()) {
                        String line = Objects.requireNonNull(responseBody.readUtf8Line());

                        // Tìm phần "response" trong JSON
                        if (line.contains("\"response\"")) {
                            String part = line.split("\"response\":\"")[1].split("\"")[0];

                            // Nếu có "\n", in ra fullResponse và xuống dòng
                            if (part.contains("\\n")) {
                                System.out.println(fullResponse);
                                fullResponse.setLength(0); // Reset chuỗi sau khi in
                            } else {
                                fullResponse.append(part);
                                System.out.print(part); // In tiếp trên cùng một dòng
                            }
                        }
                    }
                }

                // In nội dung còn lại nếu chưa có "\n"
                if (fullResponse.length() > 0) {
                    System.out.println(fullResponse);
                    fullResponse.setLength(0);
                }
            }
        });
        return "hehe";
    }
}
