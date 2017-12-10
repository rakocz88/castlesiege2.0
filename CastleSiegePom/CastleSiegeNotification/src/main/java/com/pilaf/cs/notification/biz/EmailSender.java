package com.pilaf.cs.notification.biz;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailSender {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	public String sendRegistrationEmail(String email, String username, String activationCode) {
		Context context = new Context();
		context.setVariable("username", username);
		context.setVariable("activationCode", getLinkWithActivationCode(activationCode));
		String body = templateEngine.process("template", context);
		sendEmail(email, "Castle Siege Activation", body);
		return activationCode;
	}

	private String getLinkWithActivationCode(String activationCode) { 
		//TODO hardcoded server
		return String.format("http://localhost:8080/activate/%s", activationCode);
	}

	public void sendEmail(String email, String title, String msg) {
		MimeMessage mail = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(email);
			helper.setFrom("csTeam@cs.pl");
			helper.setSubject(title);
			helper.setText(msg, true);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(mail);
	}
}
