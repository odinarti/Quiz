package repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Result;
import entity.Taked;
import entity.Test;
import entity.User;

public interface TakedRepository extends JpaRepository<Taked, Integer> {
	Optional<Taked> findByUserAndTest(User u, Test t);
	Optional<Taked> findByUserAndTestId(User u, int id);
	Set<Taked> findByTest(Test test);
	Set<Taked> findByR(Result result);
}
