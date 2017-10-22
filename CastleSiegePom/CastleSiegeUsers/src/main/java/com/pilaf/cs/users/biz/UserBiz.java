package com.pilaf.cs.users.biz;

import java.util.List;

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

	public void addUser(User user) {
		userRepository.save(user);
	}

	public User getByName(String name) {
		return userRepository.findByUsername(name);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

}