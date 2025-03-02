package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.NewWord;
import org.example.ultil.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.*;

@Service
public class MainService {
    @Autowired
    private OllamaServicePromt ollamaServicePromt;
    @Autowired
    private NewWordService newWordService;

    public void showMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            mainMenu();
            String function = scanner.nextLine();
            switch (function) {
                case "1":
                    while (true) {
                        saveNewWord(scanner);
                        System.out.printf("Nhập tiếp ? (Y/n) :  ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("n")) {
                            break;
                        }
                    }
                    break;
                case "2":
                    checkWord(scanner);
//                    translator(scanner);
                    break;
                case "3":
                    speechToText();
                    break;
                case "4":
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
                case "0":
                    System.out.println("Tạm biệt");
                    return;
            }
        }
    }

    private void checkWord(Scanner scanner) {
        while (true) {
            System.out.println(" 1 .Xem từ theo chủ đề ");
            System.out.println(" 2 .Xem từ từ mới đến cũ ");
            System.out.println(" 3 .Tạo bài kiểm tra ");
            System.out.println(" 0 .Trở lại ");
            String choose = scanner.nextLine();
            switch (choose) {
                case "1":
                    List<String> topics = newWordService.showTopic();
                    for (String topic : topics) {
                        System.out.println("Topic : " + topic);
                    }
                    if (!topics.isEmpty()) {
                        System.out.println("Mời bạn nhập topic : ");
                        String topic = scanner.nextLine();
                        List<NewWord> wordsInTopics = newWordService.showWordTopic(topic);
                        if (!wordsInTopics.isEmpty()) {
                            int maxEnglishLength = 0;
                            for (NewWord word : wordsInTopics) {
                                if (word.getEnglishWord().length() > maxEnglishLength) {
                                    maxEnglishLength = word.getEnglishWord().length();
                                }
                            }
                            printWord(wordsInTopics, maxEnglishLength);
                        }
                    }
                    break;
                case "2":
                    List<NewWord> words = newWordService.getWordOrderByTime();
                    int maxEnglishLength = 0;
                    for (NewWord word : words) {
                        if (word.getEnglishWord().length() > maxEnglishLength) {
                            maxEnglishLength = word.getEnglishWord().length();
                        }
                    }
                    printWord(words, maxEnglishLength);
                    break;
                case "3":
                    while (true) {
                        System.out.println(" 1 .Kiểm tra ngẫu nhiên Anh - Việt ");
                        System.out.println(" 2 .Kiểm tra ngẫu nhiên Việt - Anh ");
                        System.out.println(" 3 .Kiểm tra ngẫu nhiên theo chủ đề Anh - Việt");
                        System.out.println(" 4 .Kiểm tra ngẫu nhiên theo chủ đề Việt - Anh");
                        System.out.println(" 0 .Thoát ");
                        String input = scanner.nextLine();
                        if (input.equals("1") || input.equals("2")) {
                            checkMeanEV(scanner, input);
                            break;
                        }
                        if (input.equals("3")) {
                            System.out.println("hehe");
                        }
                        if (input.equals("0")) {
                            break;
                        }
                    }
                    break;
                case "0":
                    break;
            }
            if (choose.equals("0")) {
                break;
            }
        }
    }

    private void checkMeanEV(Scanner scanner, String input) {
        List<NewWord> wordList = newWordService.getWordOrderByTime();
        int wordListSize = wordList.size();
        int correct = 0;
        List<Integer> random = DataUtil.generateRandomList(Math.min(wordListSize, 25));
        for (int i = 0; i < random.size(); i++) {
            if (input.equals("1")) {
                System.out.println("Hãy cho biết ý nghĩa của từ này ? ");
                System.out.println(wordList.get(i).getEnglishWord());
            } else {
                System.out.println("Hãy cho biết từ này tiếng anh là gì ? ");
                System.out.println(wordList.get(i).getMeaning());
            }
            String answer = scanner.nextLine();
            if (input.equals("1")) {
                if (answer.trim().equalsIgnoreCase(wordList.get(i).getMeaning().trim())) {
                    System.out.println("Chính xác ! \n");
                    correct++;
                } else {
                    System.out.println("Chưa chính xác :(( ");
                    System.out.println("Đáp án là :" + wordList.get(i).getMeaning());
                }
            } else {
                if (answer.trim().equalsIgnoreCase(wordList.get(i).getEnglishWord().trim())) {
                    System.out.println("Chính xác ! \n");
                    correct++;
                } else {
                    System.out.println("Chưa chính xác :(( ");
                    System.out.println("Đáp án là :" + wordList.get(i).getEnglishWord());
                }
            }
        }
        caculatorAnswer(correct, random);
    }

    private void caculatorAnswer(int correct, List<Integer> random) {
        System.out.println("Số điểm của bạn là " + correct + "/" + random.size());
        float point = (float) (correct / random.size()) * 10;
        if (0 <= point && point < 5) {
            System.out.println(" Bạn cần cố gắng nhiều hơn :(((((((((((((((( \n");
        } else if (5 <= point && point <= 8) {
            System.out.println(" Rất tốt \n");
        } else if (point > 8) {
            System.out.println(" Tốt lắm bạn ! Hãy tiếp tục phát huy nhé ! \n");
        }
    }

    private void printWord(List<NewWord> wordsInTopics, int maxEnglishLength) {
        for (int i = 0; i < wordsInTopics.size(); i++) {
            String englishWord = wordsInTopics.get(i).getEnglishWord();
            String meaning = wordsInTopics.get(i).getMeaning();
            System.out.println("---------------------------------------------------------------------");
            // In định dạng với padding sao cho dấu | luôn thẳng hàng
            System.out.printf("%-3d: %-" + (maxEnglishLength + 2) + "s|  : %s%n", i, englishWord, meaning);
        }
        System.out.println("---------------------------------------------------------------------");
    }

    private void saveNewWord(Scanner scanner) {
        System.out.println("Từ hôm nay bạn đã học là gì ? : ");
        String newWord = scanner.nextLine();
        System.out.println("Từ này có nghĩa là gì ? : ");
        String meaning = scanner.nextLine();
        System.out.println("Chủ đề của từ này là gì ? : ");
        String topic = scanner.nextLine();
        System.out.println(newWord + "-" + meaning + "_" + topic);
        NewWord word = new NewWord();
        word.setEnglishWord(newWord);
        word.setMeaning(meaning);
        word.setIdTopic(Long.parseLong(topic));
        word.setStatus(1);
        word.setLastModifiedDate(Instant.now());
        newWordService.saveNewWord(word);
    }

    private void mainMenu() {
        System.out.println("|----------------------------------------|");
        System.out.println("|        1. Thêm từ mới.                 |");
        System.out.println("|        2. Kiểm tra từ đã học.          |");
        System.out.println("|        3. Chuyển âm thanh thành văn bản|");
        System.out.println("|        4. Trợ lý ảo                    |");
        System.out.println("|        0. Thoát chương trình           |");
        System.out.println("|----------------------------------------|");
        System.out.printf("Mời bạn nhập chức năng :  ");
    }

    private void translator(Scanner scanner) throws IOException, InterruptedException {
        System.out.println("Từ bạn muốn dịch là gì ? : ");
        String word = scanner.nextLine();
        System.out.println("Bạn muốn dịch sang ngôn ngữ gì ? : ");
        String region = scanner.nextLine();

        String encodedWord = URLEncoder.encode(word, StandardCharsets.UTF_8);
        String encodedRegion = URLEncoder.encode(region, StandardCharsets.UTF_8);
        String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl=" + encodedRegion + "&dt=t&q=" + encodedWord;

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
