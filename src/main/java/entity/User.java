package entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 259635872067783953L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Size(min = 4, max = 20)
    private String username;
    
    @Email
    private String email;
    
    @Size(min = 8, max = 32)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Test> tests;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Taked> takeds;
    
    private boolean isActivated;
    
    private String activationCode;

	public User() {}
    
    public User(String username, String email, String password) {
    	this.username = username;
    	this.setEmail(email);
    	this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
    	this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActivated;
	}
	
	public void setActivation(boolean isActivated) {
		this.isActivated = isActivated;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role r) {
		roles.add(r);
	}
	
	public Set<String> getNameRoles() {
		Set<String> s = new HashSet<String>();
		roles.forEach(role -> s.add(role.getScreenName()));
		return s;
	}

	public Set<String> getRoles() {
		Set<String> s = new HashSet<String>();
		roles.forEach(role -> s.add(role.getName()));
		return s;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    public Set<Test> getTests() {
		return tests;
	}
    
    public Set<String> getTestNames() {
    	Set<String> s = new HashSet<String>();
    	tests.forEach(test -> s.add(test.getName()));
    	return s;
    }
    
    public void addTest(Test test) {
    	tests.add(test);
    }
    
    public void deleteTest(Test test) {
    	tests.remove(test);
    }

	public void setTests(Set<Test> tests) {
		this.tests = tests;
	}

	public Set<Taked> getTakeds() {
		return takeds;
	}

	public void setTakeds(Set<Taked> answers) {
		this.takeds = answers;
	}
	
	public void addTakeds(Taked a) {
		takeds.add(a);
	}
	
	public void deleteTakeds(Taked a) {
		takeds.remove(a);
	}

	public void deleteAnswer(Answer a) {
		for(Taked t : takeds) {
			t.removeAnswer(a);
		}
	}
	
	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
}
