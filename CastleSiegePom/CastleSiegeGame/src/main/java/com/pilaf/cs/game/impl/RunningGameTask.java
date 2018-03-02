package com.pilaf.cs.game.impl;

import com.pilaf.cs.game.api.RunningGame;

public class RunningGameTask implements Runnable {

	private RunningGame runningGame;

	public RunningGameTask(RunningGame runningGame) {
		super();
		this.runningGame = runningGame;
	}

	@Override
	public void run() {
		System.out.println("Running " + runningGame.getGameId());
	}

}
