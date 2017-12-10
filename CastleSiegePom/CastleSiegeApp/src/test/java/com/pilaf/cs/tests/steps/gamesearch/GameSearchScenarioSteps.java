package com.pilaf.cs.tests.steps.gamesearch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.pilaf.cs.game.search.model.CreatedGame;
import com.pilaf.cs.tests.builder.GameBasicScenarioSingleUser;
import com.pilaf.cs.tests.builder.GameBasicScenarioTestState;
import com.pilaf.cs.tests.helper.UserPasswordHolder;
import com.pilaf.cs.tests.steps.websocket.AbstractWebsocketScenarioTestCase;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GameSearchScenarioSteps extends AbstractWebsocketScenarioTestCase {

	@Given("^AFa- There are (\\d+) players that are not logged in$")
	public void afa_There_are_players_that_are_not_logged_in(int players) throws Throwable {
		resetDataBeforeTest();
	}

	@When("^AFa- I try to log in with \"([^\"]*)\" \"([^\"]*)\"$")
	public void afa_I_try_to_log_in_with(String username, String password) throws Throwable {
		GameBasicScenarioTestState.getInstance().addToUserMap(username, new GameBasicScenarioSingleUser());
		loginTestHelper.logInWithUser(username, password,
				GameBasicScenarioTestState.getInstance().getUserMap().get(username), port);
	}

	@Then("^AFa- The user \"([^\"]*)\" should have a valid token$")
	public void afa_The_user_should_have_a_valid_token(String username) throws Throwable {
		assertThat("Token should not be null",
				GameBasicScenarioTestState.getInstance().getUserMap().get(username).getAuthorizationToken(),
				notNullValue());
	}

	@When("^AFa- The user \"([^\"]*)\" wants to start a game$")
	public void afa_The_user_wants_to_start_a_game(String username) throws Throwable {
		userWantsToStartAGame(username);
	}

	@Then("^AFa- The user \"([^\"]*)\" should be added to the list searching for a game$")
	public void afa_The_user_should_be_added_to_the_list_searching_for_a_game(String userName) throws Throwable {
		Thread.sleep(2000);
		checkIfUserIsSearchingForAGame(userName);
	}

	@When("^AFa- Another user tries to log in with \"([^\"]*)\" \"([^\"]*)\"$")
	public void afa_Another_user_tries_to_log_in_with(String username, String password) throws Throwable {
		GameBasicScenarioTestState.getInstance().addToUserMap(username, new GameBasicScenarioSingleUser());
		loginTestHelper.logInWithUser(username, password,
				GameBasicScenarioTestState.getInstance().getUserMap().get(username), port);
	}

	@Then("^AFa- The user \"([^\"]*)\" should get a msg that a game is found$")
	public void afa_The_user_should_get_a_msg_that_a_game_is_found(String username) throws Throwable {
		theUserShouldGetAMessageThatAGameIsFound(username);
	}

	@Then("^AFa- The user \"([^\"]*)\" should subscribe to the new game channel$")
	public void afa_The_user_should_subscribe_to_the_new_game_channel(String username) throws Throwable {
		String URL = String.format("ws://%s:%d/%s", "localhost", port, "game");
		websocketTestHelper.connectAndSubscribeToChannel(URL,
				"/topic/duel/" + GameBasicScenarioTestState.getInstance().getGameId(),
				GameBasicScenarioTestState.getInstance().getUserMap().get(username));
	}

	@Then("^AFa- a new instance of the game with the users \"([^\"]*)\" and \"([^\"]*)\" should be created$")
	public void afa_a_new_instance_of_the_game_with_the_users_and_should_be_created(String userName1, String userName2)
			throws Throwable {
		long user1Id = userRepository.findByUsername(userName1).getId();
		long user2Id = userRepository.findByUsername(userName2).getId();
		CreatedGame createdGame = createdGameRepository
				.findByGameId(GameBasicScenarioTestState.getInstance().getGameId());
		assertThat("Wrong user id", createdGame.getPlayer1Id(), equalTo(user1Id));
		assertThat("Wrong user id", createdGame.getPlayer2Id(), equalTo(user2Id));
	}

	@Then("^AFa- The table UserSearchGame should be empty$")
	public void afa_The_table_UserSearchGame_should_be_empty() throws Throwable {
		assertThat("User search game should be empty", userSearchGameRepository.findAll().size(), equalTo(0));
	}

	@Given("^AFb- There are (\\d+) players that are not logged in$")
	public void afb_There_are_players_that_are_not_logged_in(int arg1) throws Throwable {
		resetDataBeforeTest();
	}

	@When("^AFb- They try to log with the names \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"$")
	public void afb_They_try_to_log_with_the_names(String user1, String pass1, String user2, String pass2, String user3,
			String pass3, String user4, String pass4, String user5, String pass5) throws Throwable {
		performLoginForMultipleUsers(new UserPasswordHolder(user1, pass1), new UserPasswordHolder(user2, pass2),
				new UserPasswordHolder(user3, pass3), new UserPasswordHolder(user4, pass4),
				new UserPasswordHolder(user5, pass5));
	}

	@Then("^AFb- All the users should have valid tokens$")
	public void afb_All_the_users_should_have_valid_tokens() throws Throwable {
		checkIfAllUsersHaveValidTokens();
	}

	@When("^AFb- The users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" want to start a game$")
	public void afb_The_users_want_to_start_a_game(String user1, String user2, String user3, String user4, String user5)
			throws Throwable {
		startGameWithMultipleUsers(user1, user2, user3, user4, user5);
	}

	@Then("^AFb- (\\d+) instances of a game should be created$")
	public void afb_instances_of_a_game_should_be_created(int instancesOfTheGame) throws Throwable {
		Thread.sleep(5000);
		assertThat(String.format("%d instances of the game should be created", instancesOfTheGame),
				new Integer(createdGameRepository.findAll().size()), equalTo(new Integer(instancesOfTheGame)));
	}

	@Then("^AFb- (\\d+) users of the users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" should be given to a room$")
	public void afb_users_of_the_users_should_be_given_to_a_room(int numberOfUsers, String user1, String user2,
			String user3, String user4, String user5) throws Throwable {
		checkIfAnAmountOfUsersHasBeenGivenToARoom(numberOfUsers, user1, user2, user3, user4, user5);
	}

	@Then("^AFb- (\\d+) user of the users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" should still be searching for a room to play$")
	public void afb_user_of_the_users_should_still_be_searching_for_a_room_to_play(int amountOfUsers, String arg2,
			String arg3, String arg4, String arg5, String arg6) throws Throwable {
		long playersSearching = userSearchGameRepository.count();
		assertThat("Wrong number of users searching", playersSearching, equalTo(new Long(amountOfUsers)));
	}

	@Given("^AG- There are (\\d+) players that are not logged in$")
	public void ag_There_are_players_that_are_not_logged_in(int arg1) throws Throwable {
		resetDataBeforeTest();
	}

	@Given("^AG- The number of maximum games is set to (\\d+)$")
	public void ag_The_number_of_maximum_games_is_set_to(int maxGames) throws Throwable {
		maxHameHolder.setMaximumGames(maxGames);
	}

	@When("^AG- They try to log with the names \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"$")
	public void ag_They_try_to_log_with_the_names(String user1, String pass1, String user2, String pass2, String user3,
			String pass3, String user4, String pass4, String user5, String pass5) throws Throwable {
		performLoginForMultipleUsers(new UserPasswordHolder(user1, pass1), new UserPasswordHolder(user2, pass2),
				new UserPasswordHolder(user3, pass3), new UserPasswordHolder(user4, pass4),
				new UserPasswordHolder(user5, pass5));
	}

	@Then("^AG- All the users should have valid tokens$")
	public void ag_All_the_users_should_have_valid_tokens() throws Throwable {
		checkIfAllUsersHaveValidTokens();
	}

	@When("^AG- The users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" want to start a game$")
	public void ag_The_users_want_to_start_a_game(String user1, String user2, String user3, String user4, String user5)
			throws Throwable {
		startGameWithMultipleUsers(user1, user2, user3, user4, user5);
	}

	@Then("^AG- (\\d+) instances of a game should be created$")
	public void ag_instances_of_a_game_should_be_created(int instancesOfTheGame) throws Throwable {
		Thread.sleep(5000);
		assertThat(String.format("%d instances of the game should be created", instancesOfTheGame),
				new Integer(createdGameRepository.findAll().size()), equalTo(new Integer(instancesOfTheGame)));
	}

	@Then("^AG- restert the amount of max games to default value;$")
	public void ag_restert_the_amount_of_max_games_to_default_value() throws Throwable {
		maxHameHolder.resetToDefault();
	}

	@Given("^AFb- Wait (\\d+) seconds$")
	public void afb_Wait_seconds(int seconds) throws Throwable {
		Thread.sleep(seconds*1000);
	}
}