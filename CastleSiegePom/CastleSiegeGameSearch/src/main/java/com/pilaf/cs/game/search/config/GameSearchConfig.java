package com.pilaf.cs.game.search.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pilaf.cs.game.search.initializer.RoomLobbyPlayerFinderAsyncRunner;
import com.pilaf.cs.game.service.GameService;

@EnableAsync
@Configuration
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.pilaf.cs.game.search" })
@EnableJpaRepositories({ "com.pilaf.cs.game.search.repository" })
@EntityScan("com.pilaf.cs.game.search.model")
@PropertySource("application.game.search.properties")
public class GameSearchConfig {

	@Bean
	public GameService gameService() {
		return new GameService();
	}

	@Bean
	public RoomLobbyPlayerFinderAsyncRunner roomLobbyPlayerFinderAsyncRunner() {
		return new RoomLobbyPlayerFinderAsyncRunner();
	}

}
