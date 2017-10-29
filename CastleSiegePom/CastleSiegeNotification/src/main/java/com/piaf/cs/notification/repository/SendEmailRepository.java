package com.piaf.cs.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.piaf.cs.notification.model.SendEmail;

public interface SendEmailRepository extends JpaRepository<SendEmail, Long> {

	List<SendEmail> findByEmail(String username);
	
	SendEmail findByMessage(String message);

}
