package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Taked {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne 
	private User user;
	
	@ManyToOne
	private Test test;

	@ManyToMany(fetch = FetchType.EAGER)
    private Set<Answer> answers = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Result> r = new HashSet<>();
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
	
	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	
	public void addAnswer(Answer a) {
		answers.add(a);
	}
	
	public void removeAnswer(Answer a) {
		answers.remove(a);
	}
	
	public Set<Result> getResults() {
		return r;
	}
	
	public void setResults(Set<Result> r) {
		this.r = r;
	}
	
	public void addResult(Result res) {
		r.add(res);
	}
	
	public String resultsToString() {
		String res = "";
		for(Result result : r) {
			res += result.getDescription() + " ";
		}
		return res;
	}
}
