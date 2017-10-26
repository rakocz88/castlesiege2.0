package com.pilaf.cs.notification.biz;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.piaf.cs.notification.model.SendEmail;
import com.piaf.cs.notification.repository.SendEmailRepository;

@Component
public class EmailBiz {
	
	@Autowired
	private SendEmailRepository sendEmailRepository;
	
	@Autowired
	private EmailSender emailSender;
	
	public void sendMessage(String email, String title, String msg){
		emailSender.sendRegistrationEmail(email, email, msg);
		sendEmailRepository.save(new SendEmail(email, msg));
	}
	
	public SendEmail getLastEmailForUser(String emailAccount){
		List<SendEmail> allUserMails = sendEmailRepository.findByEmail(emailAccount).stream().sorted((h1, h2)-> h1.getTimestamp().compareTo(h2.getTimestamp())).collect(Collectors.toList());
		return allUserMails.get(allUserMails.size()-1);
	}
	
	public List<SendEmail> findAllByUser(String emailAccount){
		return sendEmailRepository.findByEmail(emailAccount);
	}


}
