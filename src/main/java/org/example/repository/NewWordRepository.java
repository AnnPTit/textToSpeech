package org.example.repository;

import org.example.domain.NewWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewWordRepository extends JpaRepository<NewWord, Long> {
    @Query(value = "select id_topic from new_word nw group by id_topic", nativeQuery = true)
    List<String> showTopic();
    @Query(value = "select * from new_word nw order by last_modified_date desc", nativeQuery = true)
    List<NewWord> getWordOrderByTime();
    @Query(value = "select * from new_word nw where id_topic = :topic ", nativeQuery = true)
    List<NewWord> showWordTopic(@Param("topic") String topic);
}
