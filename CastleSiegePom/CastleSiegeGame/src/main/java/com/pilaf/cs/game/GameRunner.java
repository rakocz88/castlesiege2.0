package com.pilaf.cs.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.pilaf.cs.game.api.MultipleGameRunner;

@Component
@EnableScheduling
@EnableAsync
public class GameRunner implements ApplicationRunner {

	@Autowired
	private MultipleGameRunner multipleGameRunner;

	public void run(ApplicationArguments arg0) throws Exception {
		multipleGameRunner.runAllGames();
	}

}
