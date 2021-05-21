package service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Quests;
import repository.AnswerRepository;
import repository.CategoryRepository;
import repository.GroupRepository;
import repository.InviteRepository;
import repository.QuestsRepository;
import repository.ResultRepository;
import repository.TakedRepository;
import repository.TestRepository;

@Service
public class TestService {
	
	@Autowired
	TestRepository testRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	ResultRepository resultRepository;
	
	@Autowired
	QuestsRepository questsRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	TakedRepository takedRepository;
	
	@Autowired
	InviteRepository inviteRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
    SmtpService smtpService;
	
	public List<Test> getAllTests() {
		return testRepository.findAll();
	}
	
	public Set<Test> getPublishedTests() {
		Set<Test> tests = new HashSet<>();
		for(Test t : getAllTests()) {
			if(t.isPublished()) tests.add(t);
		}
		return tests;
	}
	
	public Optional<Test> findTestByName(String name) {
		return testRepository.findByName(name);
	}
	
	public Optional<Test> findTestById(String id) {
		return testRepository.findById(Integer.parseInt(id));
	}
	
	public Set<Test> findTestsByAuthor(User u) {
		return testRepository.findAllByAuthor(u);
	}
	
	public boolean isUserAuthor(Test t, User u) {
		if(t.getAuthor() == null) return false;
		if(u.getAuthorities().contains(userService.getAdminRole())) return true;
		return t.getAuthor().getUsername().equals(u.getUsername());
	}
	
	public Iterable<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	public Optional<Category> findCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}
	
	public Iterable<String> getAllCategoryNames() {
		Set<String> s = new HashSet<>();
		getAllCategories().forEach(category -> s.add(category.getName()));
		return s;
	}
	
	public boolean addTest(Test t, User u, String testCategory) {
		Optional<Category> c = findCategoryByName(testCategory);
		if(testRepository.findByName(t.getName()).isEmpty() && c.isPresent()) {
			t.setAuthor(u);
			t.setCategory(c.get());
			t.setPublished(false);
			c.get().addTest(t);
			u.addTest(t);
			((User)userService.loadUserByUsername(u.getUsername())).addTest(t);
			testRepository.save(t);
			return true;
		} else {
			return false;
		}
	}
	
	public void deleteTest(String id) {
		Optional<Test> t = testRepository.findById(Integer.parseInt(id));
		if(t.isPresent()) {
			Test test = t.get();
			Optional<Category> c = categoryRepository.findByTests(test);
			if(c.isPresent()) {
				c.get().deleteTest(test);
			}
			test.getAuthor().deleteTest(test);
			for(TestGroup g : test.getGroupList()) {
				deleteGroup(id, g.getId().toString());
			}
			for(Quests q : test.getQuestsList()) {
				deleteQuestion(id, q.getId().toString());
			}
			Set<Taked> takeds = takedRepository.findByTest(test);
			for(Taked tak : takeds) {
				tak.getUser().deleteTakeds(tak);
			}
			testRepository.delete(test);
		}
	}
	
	public void editTest(String id, String name, String description, String category) {
		Optional<Test> t = findTestById(id);
		Optional<Test> ct = findTestByName(name);
		if(t.isPresent()) {
			Test test = t.get();
			if(ct.isEmpty() && name.length() > 0 && !name.equals(test.getName())) {
				test.setName(name);
			}
			if(description.length() > 0 && !description.equals(test.getDescription())) {
				test.setDescription(description);
			}
			Optional<Category> c = findCategoryByName(category);
			if(c.isPresent() && !c.get().equals(test.getCategory())) {
				c.get().addTest(test);
				test.getCategory().deleteTest(t.get());
				test.setCategory(c.get());
			}
			testRepository.save(test);
		}
	}
	
	public void addGroupToTest(String id, TestGroup g) {
		Optional<Test> t = findTestById(id);
		if(t.isPresent()) {
			Test test = t.get();
			g.setTest(test);
			test.addGroup(g);
			groupRepository.save(g);
		}
	}
	
	public void deleteGroup(String id, String groupid) {
		Optional<Test> t = testRepository.findById(Integer.parseInt(id));
		Optional<TestGroup> g = groupRepository.findById(Integer.parseInt(groupid));
		if(t.isPresent() && g.isPresent()) {
			TestGroup group = g.get();
			Test test = t.get();
			test.removeGroup(group);
			for(Result r : group.getResultList()) {
				deleteResult(id, groupid, r.getId().toString());
			}
			for(Answer a : group.getAnswerList()) {
				deleteAnswer(groupid, a.getQuests().getId().toString(), a.getId().toString());
			}
			groupRepository.deleteById(Integer.parseInt(groupid));
		}
	}

	public Optional<TestGroup> findGroupById(String groupid) {
		return groupRepository.findById(Integer.parseInt(groupid));
	}
	
	public Optional<TestGroup> findGroupByNameAndTestId(String name, String id) {
		return groupRepository.findByNameAndTestId(name, Integer.parseInt(id));
	}

	public void addResultToGroup(String id, String groupid, String description, String lowerBezel, String highBezel) {
		Optional<Test> t = findTestById(id);
		Optional<TestGroup> g = findGroupById(groupid);
		if(t.isPresent() && g.isPresent()) {
			TestGroup group = g.get();
			Result r = new Result(description, Integer.parseInt(lowerBezel), Integer.parseInt(highBezel));
			r.setGroup(group);
			group.addResult(r);
			resultRepository.save(r);
		}
	}
	
	public void deleteResult(String id, String groupid, String resultid) {
		Optional<Test> t = testRepository.findById(Integer.parseInt(id));
		Optional<TestGroup> g = groupRepository.findById(Integer.parseInt(groupid));
		Optional<Result> r = resultRepository.findById(Integer.parseInt(resultid));
		if(t.isPresent() && g.isPresent() && r.isPresent()) {
			g.get().deleteResult(r.get());
			Set<Taked> takeds = takedRepository.findByR(r.get());
			for(Taked tak : takeds) {
				tak.getUser().deleteTakeds(tak);
				takedRepository.delete(tak);
			}
			resultRepository.deleteById(Integer.parseInt(resultid));
		}
	}
	
	public void addQuestionToTest(String id, String description) {
		Optional<Test> t = findTestById(id);
		if(t.isPresent()) {
			Test test = t.get();
			Quests q = new Quests(description);
			q.setTest(test);
			test.addQuestion(q);
			questsRepository.save(q);
		}
	}

	public void deleteQuestion(String id, String questionid) {
		Optional<Test> t = testRepository.findById(Integer.parseInt(id));
		Optional<Quests> q = questsRepository.findById(Integer.parseInt(questionid));
		if(t.isPresent() && q.isPresent()) {
			t.get().deleteQuestion(q.get());
			for(Answer a : q.get().getAnswerList()) {
				deleteAnswer(a.getGroup().getId().toString(), a.getQuests().getId().toString(), a.getId().toString());
			}
			questsRepository.deleteById(Integer.parseInt(questionid));
		}
	}

	public Optional<Quests> findQuestionById(String questionid) {
		return questsRepository.findById(Integer.parseInt(questionid));
	}

	public void addAnswerToQuestion(String id, String questionid, String description, String weight, String groupname) {
		Optional<Test> t = findTestById(id);
		Optional<TestGroup> g = findGroupByNameAndTestId(groupname, id);
		Optional<Quests> q = findQuestionById(questionid);
		if(t.isPresent() && q.isPresent() && g.isPresent()) {
			Quests quests = q.get();
			TestGroup group = g.get();
			Answer a = new Answer(description, Integer.parseInt(weight));
			a.setQuests(quests);
			a.setGroup(group);
			quests.addAnswer(a);
			group.addAnswer(a);
			answerRepository.save(a);
		}
	}
	
	public void deleteAnswer(String groupid, String questionid, String answerid) {
		Optional<TestGroup> g = groupRepository.findById(Integer.parseInt(groupid));
		Optional<Quests> q = questsRepository.findById(Integer.parseInt(questionid));
		Optional<Answer> a = answerRepository.findById(Integer.parseInt(answerid));
		if(g.isPresent() && q.isPresent() && a.isPresent()) {
			g.get().deleteAnswer(a.get());
			q.get().deleteAnswer(a.get());
			answerRepository.deleteById(Integer.parseInt(answerid));
		}
	}

	public void saveAnswer(String answerid, User u) {
		Optional<Answer> a = answerRepository.findById(Integer.parseInt(answerid));
		Optional<User> dbu = userService.findUserById(u.getId());
		if(a.isPresent() && dbu.isPresent()) {
			Answer answer = a.get();
			Optional<Taked> t = takedRepository.findByUserAndTest(u, answer.getGroup().getTest());
			if(t.isPresent()) {
				t.get().addAnswer(answer);
			}
			answerRepository.save(answer);
		}
	}
	
	public Quests findNextQuestion(String id, User u) {
		Optional<Test> t = findTestById(id);
		Set<Quests> qa = new HashSet<>();
		if(t.isPresent()) {
			Optional<Taked> taked = takedRepository.findByUserAndTest(u, t.get());
			if(taked.isPresent()) {
				Set<Answer> a = taked.get().getAnswers();
				for(Answer answer : a) {
					if(answer.getGroup().getTest().equals(t.get())) {
						qa.add(answer.getQuests());
					}
				}
				for(Quests q : t.get().getQuestsList()) {
					if(!qa.contains(q)) {
						return q;
					}
				}
			} else {
				Taked tak = new Taked();
				tak.setUser(u);
				tak.setTest(t.get());
				u.addTakeds(tak);
				takedRepository.save(tak);
				for(Quests q : t.get().getQuestsList())	return q;
			}
		} else {
			return null;
		}
		return null;
	}
	
	public Set<Result> calculateResult(String id, User u) {
		Optional<Taked> taked = takedRepository.findByUserAndTestId(u, Integer.parseInt(id));
		if(taked.isPresent()) {
			Taked t = taked.get();
			Map<TestGroup, Integer> qa = new HashMap<>();
			for(Answer answer : t.getAnswers()) {
				if(qa.containsKey(answer.getGroup())) {
					qa.put(answer.getGroup(), qa.get(answer.getGroup()) + answer.getWeight());
				} else {
					qa.put(answer.getGroup(), answer.getWeight());
				}
			}
			for(Entry<TestGroup, Integer> g : qa.entrySet()) {
				Optional<Result> res = resultRepository.findByGroupAndLowerBezelLessThanEqualAndHighBezelGreaterThanEqual(g.getKey(), g.getValue(), g.getValue());
				if(res.isPresent()) {
					t.addResult(res.get());
				}
			}
			System.out.println(t.getResults());
			takedRepository.save(t);
			return t.getResults();
		}
		return null;
	}
	
	public Set<Result> getResults(String id, User u) {
		Optional<Test> test = testRepository.findById(Integer.parseInt(id));
		if(test.isPresent()) {
			Optional<Taked> taked = takedRepository.findByUserAndTest(u, test.get());
			if(taked.isPresent()) {
				return taked.get().getResults();
			}
		}
		return null;
	}
	
	public Set<Taked> getCompletedTests(User u) {
		Optional<User> us = userService.findUserById(u.getId());
		Set<Taked> takeds = new HashSet<>();
		if(us.isPresent()) {
			for(Taked t : us.get().getTakeds()) {
				if(t.getResults().size() > 0) {
					takeds.add(t);
				}
			}
		}
		return takeds;
	}

	public void clearAnswers(String id, User u) {
		Optional<Taked> taked = takedRepository.findByUserAndTestId(u, Integer.parseInt(id));
		Optional<User> dbu = userService.findUserById(u.getId());
		if(taked.isPresent() && dbu.isPresent()) {
			User user = dbu.get();
			System.out.println("answers " + taked.get().getAnswers());
			for(Answer a : taked.get().getAnswers()) {
				u.deleteAnswer(a);
				//user.deleteAnswer(a);
			}
			user.deleteTakeds(taked.get());
			u.deleteTakeds(taked.get());
			takedRepository.delete(taked.get());
		}
	}

	public void sendTestResult(String id, User u) {
		Optional<Taked> taked = takedRepository.findByUserAndTestId(u, Integer.parseInt(id));
		if(taked.isPresent()) {
			smtpService.sendTestResult(taked.get());
		}
	}

	public void publishTest(String id) {
		Optional<Test> t = testRepository.findById(Integer.parseInt(id));
		if(t.isPresent()) {
			if(t.get().isPublished()) {
				t.get().setPublished(false);
			} else {
				t.get().setPublished(true);
			}
			testRepository.save(t.get());
		}
	}

	public Set<Invite> getInvites(User u) {
		return inviteRepository.findByUser(u);
	}

	public void inviteUser(String id, String username) {
		User user = userService.findUserByUsername(username);
		Optional<Test> t = findTestById(id);
		if(user != null && t.isPresent()) {
			Test test = t.get();
			Invite invite = new Invite();
			invite.setTest(test);
			invite.setUser(user);
			smtpService.sendInvitation(user, test);
			inviteRepository.save(invite);
		}
	}

	public void closeInvite(String id, User u) {
		Set<Invite> i = inviteRepository.findByUserAndTestId(u, Integer.parseInt(id));
		for(Invite invite : i) {
			inviteRepository.delete(invite);
		}
	}
	
	public boolean isUserInvited(String id, User u) {
		Set<Invite> i = inviteRepository.findByUserAndTestId(u, Integer.parseInt(id));
		if(i.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Set<Test> search(String query) {
		Set<Test> tests = new HashSet<>();
		if(query.length() == 0) return tests;
		List<Test> all = getAllTests();
		for(Test t : all) {
			if(t.getName().contains(query)) {
				tests.add(t);
			} else if(t.getAuthor().getUsername().contains(query) && !tests.contains(t)) {
				tests.add(t);
			}
		}
		return tests;
	}
}
