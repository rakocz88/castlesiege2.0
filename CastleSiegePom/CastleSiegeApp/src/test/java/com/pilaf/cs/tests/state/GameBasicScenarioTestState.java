package com.pilaf.cs.tests.state;

import java.util.HashMap;
import java.util.Map;

public class GameBasicScenarioTestState {

	private static GameBasicScenarioTestState gameBasicScenarioTestState = new GameBasicScenarioTestState();

	private static String gameId;

	private GameBasicScenarioTestState() {
		super();
	}

	private Map<String, GameBasicScenarioSingleUser> userMap = new HashMap<>();

	public Map<String, GameBasicScenarioSingleUser> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, GameBasicScenarioSingleUser> userMap) {
		this.userMap = userMap;
	}

	public void addToUserMap(String name, GameBasicScenarioSingleUser user) {
		if (this.userMap.get(name) == null) {
			this.userMap.put(name, user);
		}

	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public static GameBasicScenarioTestState getInstance() {
		return gameBasicScenarioTestState;
	}

	public static void reset() {
		gameBasicScenarioTestState = new GameBasicScenarioTestState();
		gameId = null;
	}

}
