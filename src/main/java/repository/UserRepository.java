package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);
	Optional<User> findByActivationCode(String activationCode);
}
