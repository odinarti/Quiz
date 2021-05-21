package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entity.User;
import service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public String admin(@AuthenticationPrincipal User u) {
		return "admin";
	}
	
	@PostMapping("/settester")
	public String setTester(@RequestParam String username) {
		userService.addRole(username, "ROLE_TESTER");
		return "redirect:/admin";
	}
	
}
