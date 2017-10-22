package com.pilaf.cs.initializer;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.pilaf.cs.users.biz.UserBiz;
import com.pilaf.cs.users.model.Authority;
import com.pilaf.cs.users.model.AuthorityName;
import com.pilaf.cs.users.model.User;

@Component
public class DataInitializer implements ApplicationRunner {

	@Autowired
	private UserBiz userBiz;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		User user = new User("filip", bcryptPasswordEncoder.encode("filip"));
		user.setEnabled(true);
		user.setAuthorities(Collections.singletonList(new Authority(AuthorityName.ROLE_USER)));
		userBiz.addUser(user);
		User userAdmin = new User("admin", bcryptPasswordEncoder.encode("admin"));
		userAdmin.setEnabled(true);
		userAdmin.setAuthorities(Collections.singletonList(new Authority(AuthorityName.ROLE_ADMIN)));
		userBiz.addUser(userAdmin);
		User user2 = new User("janek", bcryptPasswordEncoder.encode("janek"));
		user2.setEnabled(true);
		user2.setAuthorities(Collections.singletonList(new Authority(AuthorityName.ROLE_USER)));
		userBiz.addUser(user2);
	}

}
