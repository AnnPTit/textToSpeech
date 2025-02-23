package org.example.domain;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "new_word")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class NewWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "english_word")
    private String englishWord;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "status")
    private Integer status;

    @Column(name = "id_topic")
    private Long idTopic;

    @Column(name = "isStudied")
    private Integer isStudied;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
}
