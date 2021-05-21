package repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Invite;
import entity.User;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
	public Set<Invite> findByUser(User u);
	public Set<Invite> findByUserAndTestId(User u, int parseInt);
}
