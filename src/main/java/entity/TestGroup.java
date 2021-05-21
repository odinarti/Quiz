package entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class TestGroup {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String name;
	
	private int maxValue;
	
	@OneToMany
	@OrderBy("id ASC")
	private Set<Answer> answerList;
	
	@OneToMany
	@OrderBy("id ASC")
	private Set<Result> resultList;

	@ManyToOne
	private Test test;
	
	public TestGroup() {}
	
	public TestGroup(String name, int maxValue) {
		this.name = name;
		this.maxValue = maxValue;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Set<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(Set<Answer> answerList) {
		this.answerList = answerList;
	}
	
	public void addAnswer(Answer a) {
		answerList.add(a);
	}
	
	public void deleteAnswer(Answer a) {
		answerList.remove(a);
	}
	
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	
	public Set<Result> getResultList() {
		return resultList;
	}
	
	public void addResult(Result r) {
		resultList.add(r);
	}
	
	public void deleteResult(Result r) {
		resultList.remove(r);
	}
	
	public void setResultList(Set<Result> resultList) {
		this.resultList = resultList;
	}
	
}
