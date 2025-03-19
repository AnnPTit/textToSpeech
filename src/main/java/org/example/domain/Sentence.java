package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sentence")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenses;

    private String sentence;

    @Column(name = "sentence_eng")
    private String sentenceEng;
}
