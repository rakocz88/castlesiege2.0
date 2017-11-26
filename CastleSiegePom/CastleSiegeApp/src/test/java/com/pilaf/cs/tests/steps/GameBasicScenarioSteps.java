package com.pilaf.cs.tests.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;

import com.pilaf.cs.game.model.CreatedGame;
import com.pilaf.cs.game.model.UserSearchGame;
import com.pilaf.cs.game.repository.CreatedGameRepository;
import com.pilaf.cs.game.repository.UserSearchGameRepository;
import com.pilaf.cs.game.service.MaximumGamesHolder;
import com.pilaf.cs.tests.builder.GameBasicScenarioSingleUser;
import com.pilaf.cs.tests.builder.GameBasicScenarioTestState;
import com.pilaf.cs.users.repository.UserRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GameBasicScenarioSteps extends AbstractCSTestCase {

	@Autowired
	private UserSearchGameRepository userSearchGameRepository;

	@Autowired
	private CreatedGameRepository createdGameRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MaximumGamesHolder maxHameHolder;

	@Given("^There are (\\d+) players that are not logged in$")
	public void there_are_players_that_are_not_logged_in(int arg1) throws Throwable {
		GameBasicScenarioTestState.reset();
		createdGameRepository.deleteAll();
		userSearchGameRepository.deleteAll();
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));
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
		userWantsToStartAGame(username);
	}

	@Then("^The user \"([^\"]*)\" should be added to the list searching for a game$")
	public void the_user_should_be_added_to_the_list_searching_for_a_game(String userName) throws Throwable {
		Thread.sleep(2000);
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
		Long userId = userRepository.findByUsername(username).getId();
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) object;
		GameBasicScenarioTestState.getInstance().setGameId((String) map.get("gameUUID"));
		Long player2Id = new Long((Integer) map.get("player2Id"));
		assertThat("User should get a message", player2Id, equalTo(userId));
	}

	@Then("^The user \"([^\"]*)\" should subscribe to the new game channel$")
	public void the_user_should_subscribe_to_the_new_game_channel(String username) throws Throwable {
		String URL = String.format("ws://%s:%d/%s", "localhost", port, "game");
		connectAndSubscribeToChannel(URL, "/topic/duel/" + GameBasicScenarioTestState.getInstance().getGameId(),
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


	@When("^They try to log with the names \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"$")
	public void they_try_to_log_with_the_names(String user1, String pass1, String user2, String pass2, String user3,
			String pass3, String user4, String pass4, String user5, String pass5) throws Throwable {
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));
		GameBasicScenarioTestState.getInstance().addToUserMap(user1, new GameBasicScenarioSingleUser());
		logInWithUser(user1, pass1, GameBasicScenarioTestState.getInstance().getUserMap().get(user1));
		GameBasicScenarioTestState.getInstance().addToUserMap(user2, new GameBasicScenarioSingleUser());
		logInWithUser(user2, pass2, GameBasicScenarioTestState.getInstance().getUserMap().get(user2));
		GameBasicScenarioTestState.getInstance().addToUserMap(user3, new GameBasicScenarioSingleUser());
		logInWithUser(user3, pass3, GameBasicScenarioTestState.getInstance().getUserMap().get(user3));
		GameBasicScenarioTestState.getInstance().addToUserMap(user4, new GameBasicScenarioSingleUser());
		logInWithUser(user4, pass4, GameBasicScenarioTestState.getInstance().getUserMap().get(user4));
		GameBasicScenarioTestState.getInstance().addToUserMap(user5, new GameBasicScenarioSingleUser());
		logInWithUser(user5, pass5, GameBasicScenarioTestState.getInstance().getUserMap().get(user5));
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));
	}

	@Then("^All the users should have valid tokens$")
	public void all_the_users_should_have_valid_tokens() throws Throwable {
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));
		for (String name : GameBasicScenarioTestState.getInstance().getUserMap().keySet()) {
			assertThat("Token should not be null",
					GameBasicScenarioTestState.getInstance().getUserMap().get(name).getAuthorizationToken(),
					notNullValue());
		}
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));
	}

	@When("^The users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" want to start a game$")
	public void the_users_want_to_start_a_game(String user1, String user2, String user3, String user4, String user5)
			throws Throwable {
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));
		userWantsToStartAGame(user1);
		userWantsToStartAGame(user2);
		userWantsToStartAGame(user3);
		userWantsToStartAGame(user4);
		userWantsToStartAGame(user5);
	}

	@Then("^(\\d+) instances of a game should be created$")
	public void instances_of_a_game_should_be_created(int instancesOfTheGame) throws Throwable {
		Thread.sleep(5000);
		assertThat(String.format("%d instances of the game should be created", instancesOfTheGame),
				new Integer(createdGameRepository.findAll().size()), equalTo(new Integer(instancesOfTheGame)));
	}

	@Then("^(\\d+) users of the users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" should be given to a room$")
	public void users_of_the_users_should_be_given_to_a_room(int numberOfUsers, String user1, String user2,
			String user3, String user4, String user5) throws Throwable {
		int playersInGame = 0;
		List<CreatedGame> createdGames = createdGameRepository.findAll();
		playersInGame += isPlayerInGame(createdGames, user1);
		playersInGame += isPlayerInGame(createdGames, user2);
		playersInGame += isPlayerInGame(createdGames, user3);
		playersInGame += isPlayerInGame(createdGames, user4);
		playersInGame += isPlayerInGame(createdGames, user5);
		assertThat("Wrong amount of players given to a room", playersInGame, equalTo(numberOfUsers));
	}

	private int isPlayerInGame(List<CreatedGame> createdGames, String user1) {
		for (CreatedGame createdGame : createdGames) {
			Long userId = userRepository.findByUsername(user1).getId();
			if (createdGame.getPlayer1Id() == userId || createdGame.getPlayer2Id() == userId) {
				return 1;
			}
		}
		return 0;
	}

	@Then("^(\\d+) user of the users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" should still be searching for a room to play$")
	public void user_of_the_users_should_still_be_searching_for_a_room_to_play(int amountOfUsers, String user1,
			String user2, String user3, String user4, String user5) throws Throwable {
		long playersSearching = userSearchGameRepository.count();
		assertThat("Wrong number of users searching", playersSearching, equalTo(new Long(amountOfUsers)));
	}

	@Then("^the game should be set as started$")
	public void the_game_should_be_set_as_started() throws Throwable {
		// // Write code here that turns the phrase above into concrete actions
		// throw new PendingException();
	}

	@Given("^The number of maximum games is set to (\\d+)$")
	public void the_number_of_maximum_games_is_set_to(int maxGames) throws Throwable {
		maxHameHolder.setMaximumGames(maxGames);
	}

	private void userWantsToStartAGame(String userName)
			throws InterruptedException, ExecutionException, TimeoutException {
		long userId = userRepository.findByUsername(userName).getId();
		String URL = String.format("ws://%s:%d/%s", "localhost", port, "game");
		connectAndSubscribeToChannel(URL, "/topic/foundGames",
				GameBasicScenarioTestState.getInstance().getUserMap().get(userName));
		CompletableFuture completableFuture = GameBasicScenarioTestState.getInstance().getUserMap().get(userName)
				.getCompletableFuture();
		StompSession stompSession = GameBasicScenarioTestState.getInstance().getUserMap().get(userName)
				.getStompSession();
		stompSession.send("/app/searchGame", userId);
	}

	@Then("^restert the amount of max games to default value;$")
	public void restert_the_amount_of_max_games_to_default_value() throws Throwable {
		maxHameHolder.resetToDefault();
	}

}
