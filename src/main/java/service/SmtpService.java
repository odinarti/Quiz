package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import entity.Taked;
import entity.Test;
import entity.User;

@Service
public class SmtpService {
	
	@Autowired
    public JavaMailSender emailSender;
	
	public void send(String mailTo, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);
	}
	
	public void sendGreetingMessage(User u) {
		send(u.getEmail(), "Система тестирования: успешная регистрация", "Здравствуйте, " + u.getUsername() + "! Вы успешно зарегистрировались в системе тестирования. Для активации учетной записи перейдите по ссылке http://localhost:8080/activate/" + u.getActivationCode());
	}
	
	public void sendTestResult(Taked t) {
		send(t.getUser().getEmail(), "Система тестирования: результаты теста", "Здравствуйте! результаты теста '" + t.getTest().getName() + "': " + t.resultsToString());
	}

	public void sendRecoverMessage(User u) {
		send(u.getEmail(), "Система тестирования: сменя пароля", "Здравствуйте! Для смены пароля проследуйте по ссылке: http://localhost:8080/recoverPassword/" + u.getActivationCode());
	}
	
	public void sendInvitation(User u, Test t) {
		send(u.getEmail(), "Система тестирования: приглашение пройти тест", "Здравствуйте! Вы приглашены пройти тест '" + t.getName() + "'. Для прохождения проследуйте по ссылке: http://localhost:8080/take/" + t.getId());
	}
	
}
