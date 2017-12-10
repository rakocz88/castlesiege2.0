package com.pilaf.cs.tests.steps.websocket;

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
import com.pilaf.cs.tests.SpringIntegrationTest;
import com.pilaf.cs.tests.builder.GameBasicScenarioSingleUser;
import com.pilaf.cs.tests.builder.GameBasicScenarioTestState;
import com.pilaf.cs.tests.helper.LoginTestHelper;
import com.pilaf.cs.tests.helper.RestEndpoints;
import com.pilaf.cs.tests.helper.UserPasswordHolder;
import com.pilaf.cs.tests.helper.WebSocketTestHelper;
import com.pilaf.cs.users.repository.UserRepository;

public abstract class AbstractWebsocketScenarioTestCase extends SpringIntegrationTest implements RestEndpoints {

	@Autowired
	protected UserSearchGameRepository userSearchGameRepository;

	@Autowired
	protected CreatedGameRepository createdGameRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected MaximumGamesHolder maxHameHolder;

	@Autowired
	protected LoginTestHelper loginTestHelper;

	@Autowired
	protected WebSocketTestHelper websocketTestHelper;

	protected int isPlayerInGame(List<CreatedGame> createdGames, String user1) {
		for (CreatedGame createdGame : createdGames) {
			Long userId = userRepository.findByUsername(user1).getId();
			if (createdGame.getPlayer1Id() == userId || createdGame.getPlayer2Id() == userId) {
				return 1;
			}
		}
		return 0;
	}

	protected void userWantsToStartAGame(String userName)
			throws InterruptedException, ExecutionException, TimeoutException {
		long userId = userRepository.findByUsername(userName).getId();
		String URL = String.format("ws://%s:%d/%s", "localhost", port, "game");
		websocketTestHelper.connectAndSubscribeToChannel(URL, "/topic/foundGames",
				GameBasicScenarioTestState.getInstance().getUserMap().get(userName));
		CompletableFuture completableFuture = GameBasicScenarioTestState.getInstance().getUserMap().get(userName)
				.getCompletableFuture();
		StompSession stompSession = GameBasicScenarioTestState.getInstance().getUserMap().get(userName)
				.getStompSession();
		stompSession.send("/app/searchGame", userId);
	}

	protected void resetDataBeforeTest() {
		GameBasicScenarioTestState.reset();
		createdGameRepository.deleteAll();
		userSearchGameRepository.deleteAll();
		assertThat("NO game should be started now", 0l, equalTo(createdGameRepository.count()));
		assertThat("NO player should be searching for a game now", 0l, equalTo(userSearchGameRepository.count()));

	}

	protected void checkIfUserIsSearchingForAGame(String userName) {
		long userId = userRepository.findByUsername(userName).getId();
		userSearchGameRepository.findAll();
		UserSearchGame userSearchGame = userSearchGameRepository.findByUserId(userId);
		assertThat("User search game should not be null", userSearchGame, notNullValue());

	}

	protected void theUserShouldGetAMessageThatAGameIsFound(String username)
			throws InterruptedException, ExecutionException, TimeoutException {
		Object object = GameBasicScenarioTestState.getInstance().getUserMap().get(username).getCompletableFuture()
				.get(100, TimeUnit.SECONDS);
		Long userId = userRepository.findByUsername(username).getId();
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) object;
		GameBasicScenarioTestState.getInstance().setGameId((String) map.get("gameUUID"));
		Long player2Id = new Long((Integer) map.get("player2Id"));
		assertThat("User should get a message", player2Id, equalTo(userId));

	}

	protected void performLoginForMultipleUsers(UserPasswordHolder... users) {
		for (UserPasswordHolder user : users) {
			GameBasicScenarioTestState.getInstance().addToUserMap(user.getLogin(), new GameBasicScenarioSingleUser());
			loginTestHelper.logInWithUser(user.getLogin(), user.getPassword(),
					GameBasicScenarioTestState.getInstance().getUserMap().get(user.getLogin()), port);
		}
	}

	protected void startGameWithMultipleUsers(String... users)
			throws InterruptedException, ExecutionException, TimeoutException {
		for (String userName : users) {
			userWantsToStartAGame(userName);
		}
	}

	protected void checkIfAllUsersHaveValidTokens() {
		for (String name : GameBasicScenarioTestState.getInstance().getUserMap().keySet()) {
			assertThat("Token should not be null",
					GameBasicScenarioTestState.getInstance().getUserMap().get(name).getAuthorizationToken(),
					notNullValue());
		}
	}

	protected void checkIfAnAmountOfUsersHasBeenGivenToARoom(int numberOfUserGiven2ARoom, String... users) {
		int playersInGame = 0;
		List<CreatedGame> createdGames = createdGameRepository.findAll();
		for (String userName : users) {
			playersInGame += isPlayerInGame(createdGames, userName);
		}
		assertThat("Wrong amount of players given to a room", playersInGame, equalTo(numberOfUserGiven2ARoom));

	}
}
