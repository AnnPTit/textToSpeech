package org.example.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Table
@Entity(name = "word")
@Data
@Builder
public class Word {
    @Id
    private String id;
    private String word;
    private String lexicalCategory;
    private String meaning;
    private String type;
    @Column(name = "is_studied")
    private Integer isStudied;

    public Word() {

    }

    public Word(String id, String word, String lexicalCategory, String meaning, String type, Integer isStudied) {
        this.id = id;
        this.word = word;
        this.lexicalCategory = lexicalCategory;
        this.meaning = meaning;
        this.type = type;
        this.isStudied = isStudied;
    }
}
