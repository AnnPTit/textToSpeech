package org.example.repository;

import org.example.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, String> {
    @Query(value = "select w.type from word w group by w.type")
    List<String> showTopic();

    @Query(value = "select * from word w where w.type =:type", nativeQuery = true)
    List<Word> showTopicContent(@Param("type") String topic);

    @Query(value = "select lexical_category from word w group by lexical_category", nativeQuery = true)
    List<String> lexicalCategory();
}
