package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Result {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;
	
	private int lowerBezel;
	
	private int highBezel;
	
	@ManyToOne
	private TestGroup group;
	
	public Result() {}

	public Result(String description, int lowerBezel, int highBezel) {
		this.description = description;	
		this.lowerBezel = lowerBezel;
		this.highBezel = highBezel;
		}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLowerBezel() {
		return lowerBezel;
	}

	public void setLowerBezel(int lowerBezel) {
		this.lowerBezel = lowerBezel;
	}

	public int getHighBezel() {
		return highBezel;
	}

	public void setMaxBezel(int highBezel) {
		this.highBezel = highBezel;
	}

	public TestGroup getGroup() {
		return group;
	}

	public void setGroup(TestGroup group) {
		this.group = group;
	}
	
}
