package com.pilaf.cs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.piaf.cs.notification.NotificationBeanConfiguration;
import com.pilaf.cs.game.config.GameConfig;
import com.pilaf.cs.users.UserBeanConfiguration;

@Configuration
@Import({ UserBeanConfiguration.class, NotificationBeanConfiguration.class, WebSocketConfig.class, GameConfig.class })
public class BeanConfig {

}
