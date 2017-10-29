package com.pilaf.cs.users.processor;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pilaf.cs.users.model.Authority;
import com.pilaf.cs.users.model.AuthorityName;
import com.pilaf.cs.users.model.User;

@Component
public class RegistrationUserProccessor {

	@Autowired
	private PasswordEncoder bcryptPasswordEncoder;

	public User proccessNewRegisteredUser(User user) {
		user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(false);
		user.setAuthorities(Collections.singletonList(new Authority(AuthorityName.ROLE_USER)));
		return user;
	}

}
