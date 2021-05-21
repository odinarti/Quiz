package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//check
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String description;
	
	private int weight;
	
	@ManyToOne
	private Quests quests;
	
	@ManyToOne
	private TestGroup group;

	public Answer() {}
	
	public Answer(String description, int weight) {
		this.description = description;
		this.weight = weight;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Quests getQuests() {
		return quests;
	}

	public void setQuests(Quests quests) {
		this.quests = quests;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public TestGroup getGroup() {
		return group;
	}

	public void setGroup(TestGroup group) {
		this.group = group;
	}
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
