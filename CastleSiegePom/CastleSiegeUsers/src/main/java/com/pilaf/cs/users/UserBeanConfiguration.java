package com.pilaf.cs.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.pilaf.cs.users.biz.UserBiz;

@Configuration
@ComponentScan(basePackages = { "com.pilaf.cs.users.rest" })
public class UserBeanConfiguration {

	@Bean
	public UserBiz userBiz() {
		return new UserBiz();
	}
}
