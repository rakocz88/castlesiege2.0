package com.pilaf.cs.game.api;

public interface MultipleGameRunner {

	public void addRunningGame(RunningGame runningGame);

	public int getCurrentAmountOfGames();
	
	public void runAllGames();

	void removeRunningGame(String gameId);

}
