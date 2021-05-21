package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Result;
import entity.TestGroup;

public interface ResultRepository extends JpaRepository<Result, Integer> {

	Optional<Result> findByGroupAndLowerBezelLessThanEqualAndHighBezelGreaterThanEqual(TestGroup key, Integer value1, Integer value2);

}
