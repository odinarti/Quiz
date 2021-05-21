package repository;

import entity.Quests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestsRepository extends JpaRepository<Quests, Integer> {

}
