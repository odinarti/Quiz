package entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Size(min = 5, max = 20)
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Test> tests;

	public Set<Test> getTests() {
		return tests;
	}
	
	public void addTest(Test t) {
		tests.add(t);
	}
	
	public void deleteTest(Test t) {
		tests.remove(t);
	}

	public void setTests(Set<Test> tests) {
		this.tests = tests;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
