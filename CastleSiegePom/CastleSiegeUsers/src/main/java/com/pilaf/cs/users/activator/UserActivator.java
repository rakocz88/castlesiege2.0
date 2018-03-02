package com.pilaf.cs.users.activator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pilaf.cs.users.model.User;
import com.pilaf.cs.users.repository.UserRepository;

@Component
public class UserActivator {

	@Autowired
	private UserRepository userRepository;

	public void activateUser(String userMail) {
		User user = userRepository.findByEmail(userMail);
		activateUser(user);
	}
	
	public void activateUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);

	}

}
