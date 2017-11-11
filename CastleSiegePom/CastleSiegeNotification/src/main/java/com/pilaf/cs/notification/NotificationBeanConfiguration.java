package com.pilaf.cs.notification;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.pilaf.cs.notification.biz.EmailBiz;

@Configuration
@ComponentScan(basePackages = { "com.pilaf.cs.notification" })
@EnableJpaRepositories({"com.piaf.cs.notification.repository"})
@EntityScan("com.piaf.cs.notification.model")
@EnableAutoConfiguration
public class NotificationBeanConfiguration {
	
	@Bean
	public EmailBiz emailBiz(){
		return new EmailBiz();
	}

}
