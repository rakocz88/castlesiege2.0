package com.pilaf.cs.users.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pilaf.cs.users.model.User;
import com.pilaf.cs.users.repository.UserRepository;

@Component
public class UserBiz {
	
	@Autowired
	private UserRepository userRepository;

	public String getSomeUser() {
		return "Jan";
	}

	public void addUser(String login, String password) {
		userRepository.save(new User(login, password));
		// TODO Auto-generated method stub
		
	}

	public User getByName(String name) {
		return userRepository.findByUsername(name);
	}

}