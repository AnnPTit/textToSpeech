package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentenceDto {
    private Long id;
    private String sentenceCorrect;
    private String sentenceCorrectEng;
    private String[] sentenceHash;
    private String[] sentenceHashEng;
}
