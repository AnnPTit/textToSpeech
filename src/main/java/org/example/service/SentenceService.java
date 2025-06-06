package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Sentence;
import org.example.dto.ResponseDto;
import org.example.dto.SentenceDto;
import org.example.repository.SentenceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SentenceService {

	private final SentenceRepository sentenceRepository;

	public List<Sentence> getAllSentence() {
		return sentenceRepository.findAll();
	}

	public List<SentenceDto> generateSentence() {
		List<SentenceDto> list = new ArrayList<>();
		List<Sentence> result = sentenceRepository.findAll();
		if (!result.isEmpty()) {
			// Thuc hien bam cau
			for (Sentence sentence : result) {
				String[] sentenceShuffle = shuffleSentence(sentence.getSentence());
				String[] sentenceShuffleEng = shuffleSentence(sentence.getSentenceEng());
				SentenceDto sentenceDto = SentenceDto.builder()
						.sentenceCorrect(sentence.getSentence())
						.sentenceHash(sentenceShuffle)
						.id(sentence.getId())
						.sentenceCorrectEng(sentence.getSentenceEng())
						.sentenceHashEng(sentenceShuffleEng)
						.build();
				list.add(sentenceDto);
			}
		}
		return list;
	}

	private String[] shuffleSentence(String sentence) {
		if (sentence == null || sentence.trim().isEmpty()) {
			return new String[0];
		}

		// Tách câu thành các từ
		String[] words = sentence.split("\\s+");

		// Trộn ngẫu nhiên các từ
		List<String> wordList = Arrays.asList(words);
		Collections.shuffle(wordList);

		// Chuyển danh sách về mảng
		return wordList.toArray(new String[0]);
	}

	public ResponseDto saveOrUpdate(Sentence sentence) {
		try {
			if (sentence.getId() == null) {
				Sentence sentence1 = sentenceRepository.findBySentence(sentence.getSentence()).orElse(null);
				if (sentence1 != null) {
					return ResponseDto.builder()
							.msg("Câu đã tồn tại")
							.status("400")
							.build();
				}
			}
			sentenceRepository.save(sentence);
		} catch (Exception e) {
			return ResponseDto.builder()
					.msg("Câu đã tồn tại")
					.status("400")
					.build();
		}
		return ResponseDto.builder()
				.msg("Thành công")
				.status("200")
				.build();
	}

	public void delete(Long id) {
		try {
			sentenceRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
