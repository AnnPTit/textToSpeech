package org.example.service;

import org.example.domain.NewWord;
import org.example.repository.NewWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {
    @Autowired
    private NewWordRepository repository;

    public void saveNewWord(NewWord word) {
        repository.save(word);
    }

    public List<String> showTopic() {
        return repository.showTopic();
    }

    public List<NewWord> getWordOrderByTime() {
        return repository.getWordOrderByTime();
    }

    public List<NewWord> showWordTopic(String topic) {
        return repository.showWordTopic(topic);
    }
}
