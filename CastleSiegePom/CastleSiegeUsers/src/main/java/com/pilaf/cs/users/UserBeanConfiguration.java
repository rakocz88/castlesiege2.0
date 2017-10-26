package com.pilaf.cs.users;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.piaf.cs.notification.NotificationBeanConfiguration;
import com.pilaf.cs.users.biz.UserBiz;

@Configuration
@Import({NotificationBeanConfiguration.class })
@ComponentScan(basePackages = { "com.pilaf.cs.users" })
@EnableJpaRepositories({"com.pilaf.cs.users.repository"})
@EntityScan("com.pilaf.cs.users.model")
public class UserBeanConfiguration {

	@Bean
	public UserBiz userBiz() {
		return new UserBiz();
	}
}
