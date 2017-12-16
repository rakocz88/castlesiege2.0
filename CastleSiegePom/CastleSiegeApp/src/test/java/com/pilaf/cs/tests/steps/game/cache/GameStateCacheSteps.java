package com.pilaf.cs.tests.steps.game.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.springframework.beans.factory.annotation.Autowired;

import com.pilaf.cs.common.api.models.GameState;
import com.pilaf.cs.game.cache.api.GameCache;
import com.pilaf.cs.tests.steps.websocket.AbstractWebsocketScenarioTestCase;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GameStateCacheSteps extends AbstractWebsocketScenarioTestCase {

	@Autowired
	private GameCache<GameState> gameCache;

	@Given("^BA- I create a game (\\d+) state with the uuid \"([^\"]*)\" and a description \"([^\"]*)\"$")
	public void ba_I_create_a_game_state_with_the_uuid_and_a_description(int gameNumber, String gameUuid, String desc)
			throws Throwable {
		GameState gameState = new GameState(gameUuid);
		gameState.setDescription(desc);
		gameCache.updateCache(gameUuid, gameState);
		
	}

	@When("^BA- I try to get the object from the cache with the uuid \"([^\"]*)\"$")
	public void ba_I_try_to_get_the_object_from_the_cache_with_the_uuid(String uuid) throws Throwable {
		GameState gameState = gameCache.getFromCache(uuid);
		GameStateCacheTestState.getInstance().putForGameUUID(uuid, gameState);
	}


	@When("^BA- I change the the description of the of the \"([^\"]*)\" game state to \"([^\"]*)\"$")
	public void ba_I_change_the_the_description_of_the_of_the_game_state_to(String uuid, String description)
			throws Throwable {
		GameState gameState = gameCache.getFromCache(uuid);
		gameState.setDescription(description);
		gameCache.updateCache(uuid, gameState);
	}

	@When("^BA- I remove the game state with the uuid  \"([^\"]*)\"$")
	public void ba_I_remove_the_game_state_with_the_uuid(String uuid) throws Throwable {
		gameCache.removeFromCache(uuid);
	}

	@When("^BA- I try to get the object with the uuid \"([^\"]*)\"$")
	public void ba_I_try_to_get_the_object_with_the_uuid(String uuid) throws Throwable {
		GameState gameState = gameCache.getFromCache(uuid);
		GameStateCacheTestState.getInstance().putForGameUUID(uuid, gameState);
	}

	
	@Then("^BA- I should get the game state with the description value \"([^\"]*)\" for the uuid \"([^\"]*)\"$")
	public void ba_I_should_get_the_game_state_with_the_description_value_for_the_uuid(String desc, String uuid) throws Throwable {
		String returnedDesc = GameStateCacheTestState.getInstance().getStateGameMap().get(uuid).getDescription();
		assertThat(returnedDesc, equalTo(desc));
	}

	@Then("^BA- I should get a null value for the uuid \"([^\"]*)\"$")
	public void ba_I_should_get_a_null_value_for_the_uuid(String uuid) throws Throwable {
	   Object obj =  GameStateCacheTestState.getInstance().getStateGameMap().get(uuid);
	   assertThat(obj, equalTo(null));
	}


}
