package org.example.service;

import jakarta.transaction.Transactional;
import org.example.domain.Word;
import org.example.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class WordService {
    @Autowired
    private WordRepository repository;

    public List<String> showTopic() {
        return repository.showTopic();
    }

    public List<Word> showTopicContent(String topic) {
        return repository.showTopicContent(topic);
    }

    public List<String> lexicalCategory() {
        return repository.lexicalCategory();
    }

    public String note(Map<String, String> request) {
        String word = request.get("word");
        String meaning = request.get("meaning");
        String lexical = request.get("lexical");
        try {
            Word entity = Word.builder()
                    .word(word)
                    .meaning(meaning)
                    .type("self-study")
                    .lexicalCategory(lexical)
                    .id(UUID.randomUUID().toString().replace("-", ""))
                    .isStudied(null)
                    .build();
            repository.save(entity);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi";
        }
    }

    public void delete(String id) {
        try {
            Word entity = repository.findById(id).orElse(null);
            if (entity != null) {
                repository.delete(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
