package com.pilaf.cs.game;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@PropertySource("classpath:application.game.properties")
@Import(GameThreadsConfig.class)
public class GameConfig {
	
	

}
