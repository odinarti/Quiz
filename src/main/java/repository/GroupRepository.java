package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.TestGroup;

public interface GroupRepository extends JpaRepository<TestGroup, Integer> {
	Optional<TestGroup> findByName(String name);
	Optional<TestGroup> findByNameAndTestId(String name, int parseInt);

}
