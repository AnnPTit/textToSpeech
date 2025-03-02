package org.example.domain;

import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity(name = "word")
@Data
public class Word {
    @Id
    private String id;
    private String word;
    private String lexicalCategory;
    private String meaning;
    private String type;
    private Integer isStudied;
}
