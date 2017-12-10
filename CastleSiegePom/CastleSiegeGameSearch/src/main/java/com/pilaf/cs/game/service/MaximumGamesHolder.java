package com.pilaf.cs.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName = "cs:name=MaximumGamesHolder")
public class MaximumGamesHolder {

	@Autowired
	private Environment env;

	@Value("${castlesiege.gamesearch.maxgames}")
	private long maximumGames;

	@ManagedAttribute
	public long getMaximumGames() {
		return maximumGames;
	}

	@ManagedAttribute
	public void setMaximumGames(long maximumGames) {
		this.maximumGames = maximumGames;
	}

	@ManagedAttribute
	public void resetToDefault() {
		Long maxGamesDefault = Long.valueOf(env.getProperty("castlesiege.gamesearch.maxgames"));
		this.setMaximumGames(maxGamesDefault);

	}

}
