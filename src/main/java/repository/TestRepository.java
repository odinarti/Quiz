package repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import entity.Test;
import entity.User;
import org.springframework.data.jpa.repository.Query;

public interface TestRepository extends JpaRepository<Test, Integer> {
	Optional<Test> findByName(String name);
	Set<Test> findAllByAuthor(User u);
}