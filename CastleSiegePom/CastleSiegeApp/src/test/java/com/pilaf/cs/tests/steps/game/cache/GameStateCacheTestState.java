package com.pilaf.cs.tests.steps.game.cache;

import java.util.HashMap;
import java.util.Map;

import com.pilaf.cs.common.api.GameState;

public class GameStateCacheTestState {

	private static GameStateCacheTestState gameStateCacheBuilder = new GameStateCacheTestState();

	private Map<String, GameState> stateGameMap = new HashMap<>(2);

	private GameStateCacheTestState() {
	}

	public static GameStateCacheTestState getInstance() {
		return gameStateCacheBuilder;
	}

	public Map<String, GameState> getStateGameMap() {
		return stateGameMap;
	}

	public void putForGameUUID(String uuid, GameState gameState) {
		stateGameMap.put(uuid, gameState);
	}

}
