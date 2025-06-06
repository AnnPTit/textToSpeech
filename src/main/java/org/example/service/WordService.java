package org.example.service;

import org.example.domain.Sentence;
import jakarta.transaction.Transactional;
import org.example.domain.Word;
import org.example.dto.SentenceDto;
import org.example.repository.SentenceRepository;
import org.example.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class WordService {
	@Autowired
	private WordRepository repository;

	@Autowired
	private SentenceRepository sentenceRepository;

	public List<String> showTopic() {
		return repository.showTopic();
	}

	public List<Word> showTopicContent(String topic) {
		return repository.showTopicContent(topic);
	}

	public List<Word> showWordStudy(Integer offset) {
		return repository.showWordStudy(offset);
	}

	public List<Word> getTotalElement(String topic) {
		return repository.getTotalElement(topic);
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
					.createdDate(new Date())
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
