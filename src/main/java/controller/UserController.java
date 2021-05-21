package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entity.User;
import service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
		if(username != null && email != null && password != null) {
			if(userService.registerUser(new User(username, email, password))) {
				model.addAttribute("message", "Вы успешно зарегистрировались! Для продолжения активируйте учетную запись с помощю ссылки, отправленной на вашу почту.");
				return "login";
			} else {
				return "register";
			}
		} else {
			return "register";
		}
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/changePassword")
	public String changePassword(@RequestParam String username) {
		userService.recoverPassword(username);
		return "redirect:/";
	}
	
	@GetMapping("/activate/{activationCode}")
	public String activateUser(Model model, @PathVariable String activationCode) {
		 if(userService.activateUser(activationCode)) {
			 model.addAttribute("message", "Пользователь активирован");
		 } else {
			 model.addAttribute("message", "Код активации не найден");
		 }
		 return "login";
	}
	
	@GetMapping("/recoverPassword/{activationCode}")
	public String recoverPassword(Model model, @PathVariable String activationCode) {
		 if(userService.isUserFoundByCode(activationCode)) {
			 model.addAttribute("activationCode", activationCode);
			 return "recoverPassword";
		 } else {
			 return "redirect:/";
		 }
	}
	
	@GetMapping("/recoverPassword")
	public String getUserPassword() {
		return "recoverPasswordUser";
	}
	
	@PostMapping("/recoverPassword")
	public String saveUserPassword(@RequestParam String activationCode, @RequestParam String newPassword) {
		 if(userService.changePassword(activationCode, newPassword)) {
			 return "redirect:/login";
		 }
		 return "redirect:/";
	}
}

