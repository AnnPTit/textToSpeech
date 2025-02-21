package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.*;

@Service
public class MainService {
    private OllamaServicePromt ollamaServicePromt = new OllamaServicePromt();
    public void showMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            mainMenu();
            Integer function = scanner.nextInt();
            scanner.nextLine();
            switch (function) {
                case 1:
                    System.out.println("Từ hôm nay bạn đã học là gì ? : ");
                    String newWord = scanner.nextLine();
                    System.out.println("Từ này có nghĩa là gì ? : ");
                    String meaning = scanner.nextLine();
                    System.out.println("Chủ đề của từ này là gì ? : ");
                    String topic = scanner.nextLine();
                    System.out.println(newWord + "-" + meaning + "_" + topic);
                    break;
                case 2:
                    System.out.println("Từ bạn muốn dịch là gì ? : ");
                    String word = scanner.nextLine();
                    System.out.println("Bạn muốn dịch sang ngôn ngữ gì ? : ");
                    String region = scanner.nextLine();
                    checkNewWord(word,region);
                    break;
                case 3:
                   speechToText();
                    break;
                case 4:
                    System.out.println("Xin chào, Bạn cần giúp gì ? ");
                    int i = 1;
                    while (i != 5) {
                        System.out.println(":");
                        String promt = scanner.nextLine();
                        String response = ollamaServicePromt.generateResponse(promt);
                        System.out.println(response);
                        if (promt.equals("Kết thúc")) {
                            i = 5;
                            break;
                        }
                    }
                case 0:
                    System.out.println("Tạm biệt");
                    return;
            }
        }
    }

    private void mainMenu() {
        System.out.println("|----------------------------------------|");
        System.out.println("|        1. Thêm từ mới.                 |");
        System.out.println("|        2. Kiểm tra từ đã học.          |");
        System.out.println("|        3. Chuyển âm thanh thành văn bản|");
        System.out.println("|        4. Trợ lý ảo                    |");
        System.out.println("|        0. Thoát chương trình           |");
        System.out.println("|----------------------------------------|");
        System.out.println("Mời bạn nhập chức năng :  ");
    }

    private void checkNewWord(String word,String region) throws IOException, InterruptedException {
        String encodedWord = URLEncoder.encode(word, StandardCharsets.UTF_8);
        String encodedRegion = URLEncoder.encode(region, StandardCharsets.UTF_8);
        String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl="+encodedRegion+"&dt=t&q=" + encodedWord;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonArray = objectMapper.readTree(response.body());
        String translatedText = jsonArray.get(0).get(0).get(0).asText();
        System.out.println("Kết quả: " + translatedText);
    }
    public void speechToText() throws Exception {
        String filePath = "C:\\Users\\phamthanhan\\Downloads\\improve-english-speaking-skills.mp3";

        // 🔹 Upload file lên AssemblyAI
        String uploadUrl = AssemblyAIService.uploadFile(filePath);
        System.out.println("Uploaded file URL: " + uploadUrl);

        AssemblyAI client = AssemblyAI.builder()
                .apiKey("dc62eb3902374d2fb83cc84616c1e715")
                .build();

        Transcript transcript = client.transcripts().transcribe("C:\\Users\\phamthanhan\\Downloads\\improve-english-speaking-skills.mp3");

        if (transcript.getStatus() == TranscriptStatus.ERROR) {
            throw new Exception("Transcript failed with error: " + transcript.getError().get());
        }

        System.out.println("Transcript: " + transcript);
    }
}
