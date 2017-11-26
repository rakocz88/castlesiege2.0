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

import com.pilaf.cs.game.search.model.CreatedGame;
import com.pilaf.cs.game.search.model.UserSearchGame;
import com.pilaf.cs.game.search.repository.CreatedGameRepository;
import com.pilaf.cs.game.search.repository.UserSearchGameRepository;
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

	@Then("^(\\d+) user of the users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" should still be searching for a room to play$")
	public void user_of_the_users_should_still_be_searching_for_a_room_to_play(int amountOfUsers, String user1,
			String user2, String user3, String user4, String user5) throws Throwable {
		long playersSearching = userSearchGameRepository.count();
		assertThat("Wrong number of users searching", playersSearching, equalTo(new Long(amountOfUsers)));
	}

	@Then("^the game should be set as started$")
	public void the_game_should_be_set_as_started() throws Throwable {
	}

	@Given("^The number of maximum games is set to (\\d+)$")
	public void the_number_of_maximum_games_is_set_to(int maxGames) throws Throwable {
		maxHameHolder.setMaximumGames(maxGames);
	}

	@Then("^restert the amount of max games to default value;$")
	public void restert_the_amount_of_max_games_to_default_value() throws Throwable {
		maxHameHolder.resetToDefault();
	}

	@Given("^AFa- There are (\\d+) players that are not logged in$")
	public void afa_There_are_players_that_are_not_logged_in(int players) throws Throwable {
		GameBasicScenarioTestState.reset();
		createdGameRepository.deleteAll();
		userSearchGameRepository.deleteAll();
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));
	}

	@When("^AFa- I try to log in with \"([^\"]*)\" \"([^\"]*)\"$")
	public void afa_I_try_to_log_in_with(String username, String password) throws Throwable {
		GameBasicScenarioTestState.getInstance().addToUserMap(username, new GameBasicScenarioSingleUser());
		logInWithUser(username, password, GameBasicScenarioTestState.getInstance().getUserMap().get(username));
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
		long userId = userRepository.findByUsername(userName).getId();
		userSearchGameRepository.findAll();
		UserSearchGame userSearchGame = userSearchGameRepository.findByUserId(userId);
		assertThat("User search game should not be null", userSearchGame, notNullValue());
	}

	@When("^AFa- Another user tries to log in with \"([^\"]*)\" \"([^\"]*)\"$")
	public void afa_Another_user_tries_to_log_in_with(String username, String password) throws Throwable {
		GameBasicScenarioTestState.getInstance().addToUserMap(username, new GameBasicScenarioSingleUser());
		logInWithUser(username, password, GameBasicScenarioTestState.getInstance().getUserMap().get(username));
	}

	@Then("^AFa- The user \"([^\"]*)\" should get a msg that a game is found$")
	public void afa_The_user_should_get_a_msg_that_a_game_is_found(String username) throws Throwable {
		Object object = GameBasicScenarioTestState.getInstance().getUserMap().get(username).getCompletableFuture()
				.get(10, TimeUnit.SECONDS);
		Long userId = userRepository.findByUsername(username).getId();
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) object;
		GameBasicScenarioTestState.getInstance().setGameId((String) map.get("gameUUID"));
		Long player2Id = new Long((Integer) map.get("player2Id"));
		assertThat("User should get a message", player2Id, equalTo(userId));
	}

	@Then("^AFa- The user \"([^\"]*)\" should subscribe to the new game channel$")
	public void afa_The_user_should_subscribe_to_the_new_game_channel(String username) throws Throwable {
		String URL = String.format("ws://%s:%d/%s", "localhost", port, "game");
		connectAndSubscribeToChannel(URL, "/topic/duel/" + GameBasicScenarioTestState.getInstance().getGameId(),
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
		// TODO Refactor common method
		GameBasicScenarioTestState.reset();
		createdGameRepository.deleteAll();
		userSearchGameRepository.deleteAll();
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));
	}

	@When("^AFb- They try to log with the names \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"$")
	public void afb_They_try_to_log_with_the_names(String user1, String pass1, String user2, String pass2, String user3,
			String pass3, String user4, String pass4, String user5, String pass5) throws Throwable {
		// TODO refactor common method
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
	}

	@Then("^AFb- All the users should have valid tokens$")
	public void afb_All_the_users_should_have_valid_tokens() throws Throwable {
		// TODO refactor
		for (String name : GameBasicScenarioTestState.getInstance().getUserMap().keySet()) {
			assertThat("Token should not be null",
					GameBasicScenarioTestState.getInstance().getUserMap().get(name).getAuthorizationToken(),
					notNullValue());
		}
	}

	@When("^AFb- The users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" want to start a game$")
	public void afb_The_users_want_to_start_a_game(String user1, String user2, String user3, String user4, String user5)
			throws Throwable {
		userWantsToStartAGame(user1);
		userWantsToStartAGame(user2);
		userWantsToStartAGame(user3);
		userWantsToStartAGame(user4);
		userWantsToStartAGame(user5);
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
		int playersInGame = 0;
		List<CreatedGame> createdGames = createdGameRepository.findAll();
		playersInGame += isPlayerInGame(createdGames, user1);
		playersInGame += isPlayerInGame(createdGames, user2);
		playersInGame += isPlayerInGame(createdGames, user3);
		playersInGame += isPlayerInGame(createdGames, user4);
		playersInGame += isPlayerInGame(createdGames, user5);
		assertThat("Wrong amount of players given to a room", playersInGame, equalTo(numberOfUsers));
	}

	@Then("^AFb- (\\d+) user of the users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" should still be searching for a room to play$")
	public void afb_user_of_the_users_should_still_be_searching_for_a_room_to_play(int amountOfUsers, String arg2,
			String arg3, String arg4, String arg5, String arg6) throws Throwable {
		long playersSearching = userSearchGameRepository.count();
		assertThat("Wrong number of users searching", playersSearching, equalTo(new Long(amountOfUsers)));
	}

	@Given("^AG- There are (\\d+) players that are not logged in$")
	public void ag_There_are_players_that_are_not_logged_in(int arg1) throws Throwable {
		// TODO refactor
		GameBasicScenarioTestState.reset();
		createdGameRepository.deleteAll();
		userSearchGameRepository.deleteAll();
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));
	}

	@Given("^AG- The number of maximum games is set to (\\d+)$")
	public void ag_The_number_of_maximum_games_is_set_to(int maxGames) throws Throwable {
		maxHameHolder.setMaximumGames(maxGames);
	}

	@When("^AG- They try to log with the names \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"  , \"([^\"]*)\" , \"([^\"]*)\"$")
	public void ag_They_try_to_log_with_the_names(String user1, String pass1, String user2, String pass2, String user3,
			String pass3, String user4, String pass4, String user5, String pass5) throws Throwable {
		// TODO refactor common method;
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
	}

	@Then("^AG- All the users should have valid tokens$")
	public void ag_All_the_users_should_have_valid_tokens() throws Throwable {
		for (String name : GameBasicScenarioTestState.getInstance().getUserMap().keySet()) {
			assertThat("Token should not be null",
					GameBasicScenarioTestState.getInstance().getUserMap().get(name).getAuthorizationToken(),
					notNullValue());
		}
	}

	@When("^AG- The users \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" want to start a game$")
	public void ag_The_users_want_to_start_a_game(String user1, String user2, String user3, String user4, String user5)
			throws Throwable {
		userWantsToStartAGame(user1);
		userWantsToStartAGame(user2);
		userWantsToStartAGame(user3);
		userWantsToStartAGame(user4);
		userWantsToStartAGame(user5);
	}

	@Then("^AG- (\\d+) instances of a game should be created$")
	public void ag_instances_of_a_game_should_be_created(int instancesOfTheGame) throws Throwable {
		/// TODO refactor
		Thread.sleep(5000);
		assertThat(String.format("%d instances of the game should be created", instancesOfTheGame),
				new Integer(createdGameRepository.findAll().size()), equalTo(new Integer(instancesOfTheGame)));
	}

	@Then("^AG- restert the amount of max games to default value;$")
	public void ag_restert_the_amount_of_max_games_to_default_value() throws Throwable {
		maxHameHolder.resetToDefault();
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

}
