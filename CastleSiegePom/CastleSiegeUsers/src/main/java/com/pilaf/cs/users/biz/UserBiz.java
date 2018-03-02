package com.pilaf.cs.users.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pilaf.cs.notification.biz.EmailBiz;
import com.pilaf.cs.users.activator.UserActivator;
import com.pilaf.cs.users.model.ESUser;
import com.pilaf.cs.users.model.User;
import com.pilaf.cs.users.processor.RegistrationUserProccessor;
import com.pilaf.cs.users.repository.ESUserRepository;
import com.pilaf.cs.users.repository.UserRepository;
import com.pilaf.cs.users.utils.UserUtil;
import com.pilaf.cs.users.validator.UserValidator;

@Component
public class UserBiz {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ESUserRepository esUserRepository;

	@Autowired
	private EmailBiz emailBiz;

	@Autowired
	private RegistrationUserProccessor userProccessor;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private ESUserUpdater esUserUpdater;

	@Autowired
	private UserActivator userActivator;

	public void addUser(User user) {
		userRepository.save(user);
	}

	public User getByName(String name) {
		return userRepository.findByUsername(name);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User registerUser(User user) {
		userValidator.validateRegisteredUser(user);
		User proccessedUser = userProccessor.proccessNewRegisteredUser(user);
		esUserRepository.findAll();
		userRepository.save(proccessedUser);
		esUserRepository.save(new ESUser(proccessedUser));
		emailBiz.sendMessage(proccessedUser.getEmail(), "Register User", userUtil.generateActivationCodeLink());
		esUserRepository.findAll();
		return proccessedUser;
	}

	public String activateUser(String token) {
		String userEmail = emailBiz.findEmailByToken(token);
		User user = userRepository.findByEmail(userEmail);
		userActivator.activateUser(user);
		esUserUpdater.updateUser(user);
		return "User Active";
	}

}