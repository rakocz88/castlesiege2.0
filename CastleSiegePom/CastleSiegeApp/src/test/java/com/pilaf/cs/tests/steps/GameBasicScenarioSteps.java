package com.pilaf.cs.tests.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;

import com.pilaf.cs.game.model.CreatedGame;
import com.pilaf.cs.game.model.UserSearchGame;
import com.pilaf.cs.game.repository.CreatedGameRepository;
import com.pilaf.cs.game.repository.UserSearchGameRepository;
import com.pilaf.cs.tests.builder.GameBasicScenarioSingleUser;
import com.pilaf.cs.tests.builder.GameBasicScenarioTestState;
import com.pilaf.cs.tests.builder.StompInfoHolder;
import com.pilaf.cs.users.repository.UserRepository;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GameBasicScenarioSteps extends AbstractCSTestCase {

	private static final String HARDCODED_GAME_ID = "d6fb61dc-8236-4cd4-9d31-92c4e9a676c8";

	@Autowired
	private UserSearchGameRepository userSearchGameRepository;

	@Autowired
	private CreatedGameRepository createdGameRepository;

	@Autowired
	private UserRepository userRepository;

	@Given("^There are (\\d+) players that are not logged in$")
	public void there_are_players_that_are_not_logged_in(int arg1) throws Throwable {
	}

	@When("^I try to log in with \"([^\"]*)\" \"([^\"]*)\"$")
	public void i_try_to_log_in_with(String username, String password) throws Throwable {
		GameBasicScenarioTestState.getInstance().addToUserMap(username, new GameBasicScenarioSingleUser());
		logInWithUser(username, password, GameBasicScenarioTestState.getInstance().getUserMap().get(username));
	}

	@Then("^The user \"([^\"]*)\" should have a valid token$")
	public void the_user_should_have_a_valid_token(String username) throws Throwable {
		assertThat("Token should not be null",
				GameBasicScenarioTestState.getInstance().getUserMap().get(username).getAuthorizationToken(),
				notNullValue());
	}

	@When("^The user \"([^\"]*)\" wants to start a game$")
	public void the_user_wants_to_start_a_game(String username) throws Throwable {
		long userId = userRepository.findByUsername(username).getId();
		String URL = String.format("ws://%s:%d/%s", "localhost", port, "game");
		connectAndSubscribeToChannel(URL, "/topic/foundGames",
				GameBasicScenarioTestState.getInstance().getUserMap().get(username));
		CompletableFuture completableFuture = GameBasicScenarioTestState.getInstance().getUserMap().get(username)
				.getCompletableFuture();
		StompSession stompSession = GameBasicScenarioTestState.getInstance().getUserMap().get(username)
				.getStompSession();
		stompSession.send("/app/searchGame", userId);
		System.out.println("DZIAŁA");
	}

	@Then("^The user \"([^\"]*)\" should be added to the list searching for a game$")
	public void the_user_should_be_added_to_the_list_searching_for_a_game(String userName) throws Throwable {
		Thread.sleep(5000);
		long userId = userRepository.findByUsername(userName).getId();
		userSearchGameRepository.findAll();
		UserSearchGame userSearchGame = userSearchGameRepository.findByUserId(userId);
		assertThat("User search game should not be null", userSearchGame, notNullValue());
	}

	@When("^Another user tries to log in with \"([^\"]*)\" \"([^\"]*)\"$")
	public void another_user_tries_to_log_in_with(String username, String password) throws Throwable {
		GameBasicScenarioTestState.getInstance().addToUserMap(username, new GameBasicScenarioSingleUser());
		logInWithUser(username, password, GameBasicScenarioTestState.getInstance().getUserMap().get(username));
	}

	@Then("^The user \"([^\"]*)\" should get a msg that a game is found$")
	public void the_user_should_get_a_msg_that_a_game_is_found(String username) throws Throwable {
		Object object = GameBasicScenarioTestState.getInstance().getUserMap().get(username).getCompletableFuture()
				.get(10, TimeUnit.SECONDS);
		// GameBasicScenarioTestState.getInstance().getUserMap().get(username).getStompSession()
		// The game Id is returned in the message, For now it is hardcoded
		Long userId = userRepository.findByUsername(username).getId();
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) object;
		GameBasicScenarioTestState.getInstance().setGameId((String) map.get("gameUUID"));
		Long player2Id = new Long((Integer) map.get("player2Id"));
		assertThat("User should get a message", player2Id, equalTo(userId));
	}

	@Then("^The user \"([^\"]*)\" should subscribe to the new game channel$")
	public void the_user_should_subscribe_to_the_new_game_channel(String username) throws Throwable {
		connectAndSubscribeToChannel("game", "/duel/" + GameBasicScenarioTestState.getInstance().getGameId(),
				GameBasicScenarioTestState.getInstance().getUserMap().get(username));
	}

	@Then("^a new instance of the game with the users \"([^\"]*)\" and \"([^\"]*)\" should be created$")
	public void a_new_instance_of_the_game_with_the_users_and_should_be_created(String userName1, String userName2)
			throws Throwable {
		long user1Id = userRepository.findByUsername(userName1).getId();
		long user2Id = userRepository.findByUsername(userName2).getId();
		CreatedGame createdGame = createdGameRepository
				.findByGameId(GameBasicScenarioTestState.getInstance().getGameId());
		assertThat("Wrong user id", createdGame.getPlayer1Id(), equalTo(user1Id));
		assertThat("Wrong user id", createdGame.getPlayer2Id(), equalTo(user2Id));
	}

	@Then("^The table UserSearchGame should be empty$")
	public void the_table_UserSearchGame_should_be_empty() throws Throwable {
		assertThat("User search game should be empty", userSearchGameRepository.findAll().size(), equalTo(0));
	}

	@Then("^The user \"([^\"]*)\" should no longer subscribe to the channel \"([^\"]*)\"$")
	public void the_user_should_no_longer_subscribe_to_the_channel(String arg1, String arg2) throws Throwable {
		// // Write code here that turns the phrase above into concrete actions
		// throw new PendingException();
	}

	@Given("^There are (\\d+) users that are not logged in$")
	public void there_are_users_that_are_not_logged_in(int arg1) throws Throwable {
		// // Write code here that turns the phrase above into concrete actions
		// throw new PendingException();
	}

	@When("^They try to log with the names \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"$")
	public void they_try_to_log_with_the_names(String arg1, String arg2, String arg3, String arg4, String arg5,
			String arg6, String arg7, String arg8, String arg9, String arg10) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^All the users should have valid tokens$")
	public void all_the_users_should_have_valid_tokens() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^The users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" want to start a game$")
	public void the_users_want_to_start_a_game(String arg1, String arg2, String arg3, String arg4, String arg5)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^(\\d+) instances of a game should be created$")
	public void instances_of_a_game_should_be_created(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^(\\d+) users of the users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" should be given to a room$")
	public void users_of_the_users_should_be_given_to_a_room(int arg1, String arg2, String arg3, String arg4,
			String arg5, String arg6) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^(\\d+) user of the users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" should still be searching for a room to play$")
	public void user_of_the_users_should_still_be_searching_for_a_room_to_play(int arg1, String arg2, String arg3,
			String arg4, String arg5, String arg6) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^one of the game rooms is selected$")
	public void one_of_the_game_rooms_is_selected() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^two of the users send a \"([^\"]*)\" event$")
	public void two_of_the_users_send_a_event(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^the game should be set as started$")
	public void the_game_should_be_set_as_started() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^the game should have set two users$")
	public void the_game_should_have_set_two_users() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^the two users should recieve s STOMP message with the event \"([^\"]*)\"$")
	public void the_two_users_should_recieve_s_STOMP_message_with_the_event(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Given("^The number of maximum games is set to (\\d+)$")
	public void the_number_of_maximum_games_is_set_to(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}
