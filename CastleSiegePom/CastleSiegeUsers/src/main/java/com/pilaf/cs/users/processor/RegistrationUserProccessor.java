package com.pilaf.cs.users.processor;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.pilaf.cs.users.model.Authority;
import com.pilaf.cs.users.model.AuthorityName;
import com.pilaf.cs.users.model.User;

@Component
public class RegistrationUserProccessor {
	
	public User proccessNewRegisteredUser(User user){
		user.setEnabled(false);
		user.setAuthorities(Collections.singletonList(new Authority(AuthorityName.ROLE_USER)));
		return user;
	}

}
