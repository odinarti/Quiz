package controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entity.Quests;
import entity.Result;
import entity.Test;
import entity.User;
import service.TestService;
import service.UserService;

@Controller
public class MainController {

	@Autowired
	TestService testService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("testList", testService.getPublishedTests());
		return "index";
	}

	@GetMapping("/home")
	public String home(Model model) {
		return "home";
	}
	
	@GetMapping("/search")
	public String search(Model model) {
		model.addAttribute("categories", testService.getAllCategoryNames());
		return "search";
	}
	
	@PostMapping("/search")
	public String searchRes(Model model, @RequestParam String query) {
		model.addAttribute("result", testService.search(query));
		model.addAttribute("categories", testService.getAllCategoryNames());
		return "search";
	}
	
	@GetMapping("/profile")
	public String profile(@AuthenticationPrincipal User u, Model model) {
		model.addAttribute("invites", testService.getInvites(u));
		model.addAttribute("completedTests", testService.getCompletedTests(u));
		model.addAttribute("tests", testService.findTestsByAuthor(u));
		return "profile";
	}
	
	@GetMapping("/create")
	public String createTest(@AuthenticationPrincipal User u, Model model) {
		model.addAttribute("categories", testService.getAllCategoryNames());
		return "testCreation";
	}
	
	@PostMapping("/create")
	public String createTest(@AuthenticationPrincipal User u, @RequestParam String testName, @RequestParam String testDescription, @RequestParam String testCategory) {
		if(testName.length() > 0 && testDescription.length() > 0 && testCategory.length() > 0) {
			testService.addTest(new Test(testName, testDescription), u, testCategory);
			Optional<Test> t = testService.findTestByName(testName);
			if(t.isPresent()) {
				return "redirect:/test/edit?id=" + t.get().getId();
			} else {
				return "redirect:/create?alert";
			}
		} else {
			return "redirect:/create?alert";
		}
	}
	
	@GetMapping("/take/{id}")
	public String takeTest(@AuthenticationPrincipal User u, @PathVariable String id, Model model) {
		Optional<Test> t = testService.findTestById(id);
		if(t.isPresent() && (t.get().isPublished() || testService.isUserInvited(id, u))) {
			model.addAttribute("test", t.get());
			Quests q = testService.findNextQuestion(id, u);
			if(q == null) {
				Set<Result> r = testService.getResults(id, u);
				if(r.size() != 0) {
					model.addAttribute("results", r);
				} else {
					model.addAttribute("results", testService.calculateResult(id, u));
				}
				
			} else {
				model.addAttribute("question", q);
			}
			return "takeTest";
		}
		return "redirect:/";
	}
	
	@PostMapping("/take")
	public String saveAnswer(@AuthenticationPrincipal User u, @RequestParam String id, @RequestParam(defaultValue = "") String answerid) {
		if(!answerid.equals("")) {
			testService.saveAnswer(answerid, u);
		}
		return "redirect:/take/" + id;
	}
	
	@GetMapping("/retry")
	public String retryTest(@AuthenticationPrincipal User u, @RequestParam String id) {
		testService.clearAnswers(id, u);
		return "redirect:/take/" + id;
	}
	
	@GetMapping("/send")
	public String sendResultsToEmail(@AuthenticationPrincipal User u, @RequestParam String id) {
		testService.sendTestResult(id, u);
		return "/";
	}
	
	@GetMapping("closeInvite") 
	public String closeInvite(@AuthenticationPrincipal User u, @RequestParam String id) {
		testService.closeInvite(id, u);
		return "redirect:/profile";
	}
	
}