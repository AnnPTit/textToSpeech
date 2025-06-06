package org.example.repository;

import org.example.domain.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long> {
	Optional<Sentence> findBySentence(String sentence);
}
