package controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entity.Quests;
import entity.Test;
import entity.TestGroup;
import entity.User;
import service.SmtpService;
import service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	TestService testService;
	
	@Autowired
    SmtpService smtpService;
	
	@GetMapping("/edit")
	public String editTest(@AuthenticationPrincipal User u, @RequestParam String id, Model model) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			model.addAttribute("test", t.get());
			model.addAttribute("categories", testService.getAllCategoryNames());
			return "testEdit";
		} else {
			return "redirect:/create";
		}
	}
	
	@PostMapping("/edit")
	public String saveEditTest(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String testName, @RequestParam String testDescription, @RequestParam String testCategory) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			testService.editTest(id, testName, testDescription, testCategory);
			return "redirect:/test/edit?id=" + id;
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/publish")
	public String publishTest(@AuthenticationPrincipal User u, @RequestParam String id) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			testService.publishTest(id);
			return "redirect:/test/edit?id=" + id;
		} else {
			return "redirect:/";
		}
	}
	
	@PostMapping("/invite")
	public String inviteUser(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String username) {
		Optional<Test> t = testService.findTestById(id);
		if(username.length() > 0 && t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			testService.inviteUser(id, username);
		}
		return "redirect:/test/edit?id=" + id;
	}
	
	@GetMapping("/delete")
	public String deleteTest(@AuthenticationPrincipal User u, @RequestParam String id) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			testService.deleteTest(id);
			return "redirect:/profile";
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/addgroups")
	public String addGroups(@AuthenticationPrincipal User u, @RequestParam String id, Model model) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			model.addAttribute("test", t.get());
			return "addgroups";
		} else {
			return "redirect:/create";
		}
	}
	
	@PostMapping("/addgroups")
	public String addGroup(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String groupName, @RequestParam String maxValue) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u) && groupName.length() > 0 && maxValue.length() > 0) {
			testService.addGroupToTest(id, new TestGroup(groupName, Integer.parseInt(maxValue)));
			return "redirect:/test/addgroups?id=" + id;
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/deletegroup")
	public String deleteGroup(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String groupid) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			testService.deleteGroup(id, groupid);
			return "redirect:/test/edit?id=" + id;
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/addresults")
	public String addResults(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String groupid, Model model) {
		Optional<Test> t = testService.findTestById(id);
		Optional<TestGroup> g = testService.findGroupById(groupid);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			model.addAttribute("test", t.get());
			model.addAttribute("group", g.get());
			return "addresults";
		} else {
			return "redirect:/create";
		}
	}
	
	@PostMapping("/addresults")
	public String addResult(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String groupid, @RequestParam String description, @RequestParam String lowerBezel, @RequestParam String highBezel) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u) && description.length() > 0 && lowerBezel.length() > 0 && highBezel.length() > 0) {
			testService.addResultToGroup(id, groupid, description, lowerBezel, highBezel);
			return "redirect:/test/addresults?id=" + id + "&groupid=" + groupid;
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/deleteresult")
	public String deleteResult(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String groupid, @RequestParam String resultid) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			testService.deleteResult(id, groupid, resultid);
			return "redirect:/test/edit?id=" + id;
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/addquestions")
	public String addQuestions(@AuthenticationPrincipal User u, @RequestParam String id, Model model) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			model.addAttribute("test", t.get());
			return "addquestions";
		} else {
			return "redirect:/create";
		}
	}
	
	@PostMapping("/addquestions")
	public String addQuestion(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String description) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u) && description.length() > 0) {
			testService.addQuestionToTest(id, description);
			return "redirect:/test/addquestions?id=" + id;
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/deletequestion")
	public String deleteQuestion(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String questionid) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			testService.deleteQuestion(id, questionid);
			return "redirect:/test/edit?id=" + id;
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/addanswers")
	public String addAnswers(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String questionid, Model model) {
		Optional<Test> t = testService.findTestById(id);
		Optional<Quests> q = testService.findQuestionById(questionid);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			model.addAttribute("test", t.get());
			model.addAttribute("question", q.get());
			return "addanswers";
		} else {
			return "redirect:/create";
		}
	}
	
	@PostMapping("/addanswers")
	public String addAnswer(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String questionid, @RequestParam String description, @RequestParam String weight, @RequestParam String group) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u) && description.length() > 0 && weight.length() > 0 && group.length() > 0) {
			testService.addAnswerToQuestion(id, questionid, description, weight, group);
			return "redirect:/test/addanswers?id=" + id + "&questionid=" + questionid;
		} else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/deleteanswer")
	public String deleteAnswer(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam String groupid, @RequestParam String questionid, @RequestParam String answerid) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && testService.isUserAuthor(t.get(), u)) {
			testService.deleteAnswer(groupid, questionid, answerid);
			return "redirect:/test/edit?id=" + id;
		} else {
			return "redirect:/";
		}
	}
}
