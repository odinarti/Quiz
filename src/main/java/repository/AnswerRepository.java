package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
