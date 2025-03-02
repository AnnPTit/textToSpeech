package org.example.service;

import org.example.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {
    @Autowired
    private WordRepository repository;

    public List<String> showTopic() {
        return repository.showTopic();
    }

    public List<String> showTopicContent(String topic) {
        return repository.showTopicContent(topic);
    }
}
