package service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import entity.Role;
import entity.User;
import repository.RoleRepository;
import repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@PersistenceContext
    private EntityManager em;
	
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    SmtpService smtpService;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails u = userRepository.findByUsername(username);
		if(u == null) {
			throw new UsernameNotFoundException("Пользователь не найден");
		} else {
			return u;
		}
		
	}
	
	public boolean isUserRegistered(String username) {
		return userRepository.findByUsername(username) != null;
	}
	
	public Role getAdminRole() {
		return roleRepository.findByName("ROLE_ADMIN");
	}
	
	public boolean isUserHasRole(User u, String role) {
		if(u.getRoles().contains(role)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean registerUser(User u) {
		if(!isUserRegistered(u.getUsername())) {
			u.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
	        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
	        u.setActivation(false);
	        u.setActivationCode(UUID.randomUUID().toString());
			userRepository.save(u);
			if(u.getUsername().equals("admin")) {
				addRole(u.getUsername(), "ROLE_ADMIN");
			}
			smtpService.sendGreetingMessage(u);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean activateUser(String activationCode) {
		Optional<User> u = userRepository.findByActivationCode(activationCode);
		if(u.isPresent()) {
			User user = u.get();
			user.setActivationCode(null);
	        user.setActivation(true);
	        userRepository.save(user);
	        return true;
		}
		return false;
	}
	
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}
	
	public User findUserByUsername(String username) {
		return (User)userRepository.findByUsername(username);
	}
	
	public void addRole(String username, String name) {
		User user = (User) userRepository.findByUsername(username);
		Role r = roleRepository.findByName(name);
		if(user != null && r != null) {
			user.addRole(r);
			userRepository.save(user);
		}
	}

	public void recoverPassword(String username) {
		User user = findUserByUsername(username);
		if(user != null) {
			if(user.isEnabled()) {
				user.setActivationCode(UUID.randomUUID().toString());
				userRepository.save(user);
				smtpService.sendRecoverMessage(user);
			}
		}
	}
	
	public boolean isUserFoundByCode(String code) {
		return userRepository.findByActivationCode(code).isPresent();
	}
	public boolean changePassword(String code, String newPassword) {
		Optional<User> user = userRepository.findByActivationCode(code);
		if(user.isPresent()) {
			user.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
			userRepository.save(user.get());
			return true;
		}
		return false;
	}
	
}
