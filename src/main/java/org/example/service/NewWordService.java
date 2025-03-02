package org.example.service;

import org.example.domain.NewWord;
import org.example.repository.NewWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewWordService {
    @Autowired
    private NewWordRepository newRepository;

    public void saveNewWord(NewWord word) {
        newRepository.save(word);
    }

    public List<String> showTopic() {
        return newRepository.showTopic();
    }

    public List<NewWord> getWordOrderByTime() {
        return newRepository.getWordOrderByTime();
    }

    public List<NewWord> showWordTopic(String topic) {
        return newRepository.showWordTopic(topic);
    }
}
