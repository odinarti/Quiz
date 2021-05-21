package entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
public class Test {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Size(min = 5, max = 100)
	private String name;
	
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Size(min = 3, max = 140)
	private String description;

	@OneToMany
	@OrderBy("id ASC")
	private Set<TestGroup> groupList;
	
	@OneToMany
	@OrderBy("id ASC")
	private Set<Quests> questsList;

	@ManyToOne
	private Category category;
	
	@ManyToOne
	private User author;
	
	private boolean published;

	public Test() {}
	
	public Test(String name, String description) {
		this.name = name;
		this.description = description;
		this.creationDate = new Date();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<TestGroup> getGroupList() {
		return groupList;
	}
	
	public Set<String> getGroupListName() {
		Set<String> s = new HashSet<String>();
		groupList.forEach(group -> s.add(group.getName()));
		return s;
	}

	public void setGroupList(Set<TestGroup> groupList) {
		this.groupList = groupList;
	}
	
	public void addGroup(TestGroup g) {
		groupList.add(g);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void removeGroup(TestGroup g) {
		groupList.remove(g);
	}
	
	public Set<Quests> getQuestsList() {
		return questsList;
	}

	public void setQuestsList(Set<Quests> questsList) {
		this.questsList = questsList;
	}
	
	public void addQuestion(Quests q) {
		questsList.add(q);
	}
	
	public void deleteQuestion(Quests q) {
		questsList.remove(q);
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}
	
}