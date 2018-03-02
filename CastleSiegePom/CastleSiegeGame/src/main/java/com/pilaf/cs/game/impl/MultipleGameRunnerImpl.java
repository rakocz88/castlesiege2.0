package com.pilaf.cs.game.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pilaf.cs.game.api.MultipleGameRunner;
import com.pilaf.cs.game.api.RunningGame;

@Component
@EnableAsync
@EnableScheduling
public class MultipleGameRunnerImpl implements MultipleGameRunner {

	private Logger logger = Logger.getLogger(MultipleGameRunnerImpl.class.getName());

	@Autowired
	private TaskExecutor taskExecutor;

	private List<RunningGame> runningGames = new ArrayList<>();

	@Override
	public void addRunningGame(RunningGame runningGame) {
		runningGames.add(runningGame);
	}

	@Override
	public void removeRunningGame(String gameId) {
		RunningGame removeGame = runningGames.stream().filter(runningGame -> runningGame.getGameId().equals(gameId))
				.collect(Collectors.toList()).get(0);
		runningGames.remove(removeGame);
	}

	@Override
	public int getCurrentAmountOfGames() {
		return runningGames.size();

	}

	@Override
	@Async
	@Scheduled(fixedDelay = 1000)
	public void runAllGames() {
		if (runningGames.isEmpty()) {
			logger.log(Level.FINEST, "No game is running");
		}
		runningGames.stream().forEach(elem -> taskExecutor.execute(new RunningGameTask(elem)));

	}

}
