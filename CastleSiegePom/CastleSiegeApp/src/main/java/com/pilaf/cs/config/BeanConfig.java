package com.pilaf.cs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pilaf.cs.game.search.config.GameSearchConfig;
import com.pilaf.cs.notification.NotificationBeanConfiguration;
import com.pilaf.cs.users.UserBeanConfiguration;

@Configuration
@Import({ UserBeanConfiguration.class, NotificationBeanConfiguration.class, WebSocketConfig.class, GameSearchConfig.class })
public class BeanConfig {

}
