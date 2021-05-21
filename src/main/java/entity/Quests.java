package entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

@Entity
public class Quests {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Size(min = 2, max = 200)
	private String description;
	
	@OneToMany
	@OrderBy("id ASC")
	private Set<Answer> answerList;

	@ManyToOne
	private Test test;

	public Quests() {}
	
	public Quests(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addAnswer(Answer a) {
		answerList.add(a);
	}
	
	public void deleteAnswer(Answer a) {
		answerList.remove(a);
	}
	
	public Set<Answer> getAnswerList() {
		return answerList;
	}
	
	public void setAnswerList(Set<Answer> answerList) {
		this.answerList = answerList;
	}
	
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
