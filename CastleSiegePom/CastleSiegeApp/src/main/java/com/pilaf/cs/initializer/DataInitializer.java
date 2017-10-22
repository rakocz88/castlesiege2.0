package com.pilaf.cs.initializer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.pilaf.cs.users.biz.UserBiz;

@Component
public class DataInitializer implements ApplicationRunner {
	
	@Autowired
	private UserBiz userBiz;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		userBiz.addUser("filip", bcryptPasswordEncoder.encode("filip"));
	}

}

