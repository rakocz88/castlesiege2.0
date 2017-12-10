package com.pilaf.cs.game.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.pilaf.cs.game.seach.message.GameFoundMessage;
import com.pilaf.cs.game.search.model.CreatedGame;
import com.pilaf.cs.game.search.model.UserSearchGame;
import com.pilaf.cs.game.search.repository.CreatedGameRepository;

@Service
public class GameCreator {

	@Autowired
	private CreatedGameRepository createdGameRepository;

	@Autowired
	private CreateGameValidator createGameValidator;

	private Logger log = Logger.getLogger(GameCreator.class.getName());

	@Async
	public Future<GameFoundMessage> createNewGame(List<UserSearchGame> usersFoundForGame) {
		log.info("Try to create game for player" + usersFoundForGame.get(0).getUserId() + " and player " + usersFoundForGame.get(1).getUserId());
		createGameValidator.validateBeforeGameCreation(usersFoundForGame);
		UserSearchGame user1 = usersFoundForGame.get(0);
		UserSearchGame user2 = usersFoundForGame.get(1);
		UUID gameUUID = UUID.randomUUID();
		createGameForPlayers(gameUUID, user1, user2);
		return new AsyncResult<GameFoundMessage>(
				new GameFoundMessage(gameUUID.toString(), user1.getUserId(), user2.getUserId()));
	}

	@Async
	private void createGameForPlayers(UUID gameUUID, UserSearchGame userId1, UserSearchGame userId2) {
		log.info("Game created for " + userId1.getUserId() + " and " + userId2.getUserId());
		createdGameRepository.save(new CreatedGame(userId1.getUserId(), userId2.getUserId(), gameUUID.toString()));
	}
}
