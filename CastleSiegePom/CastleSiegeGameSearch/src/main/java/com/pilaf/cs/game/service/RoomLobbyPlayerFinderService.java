package com.pilaf.cs.game.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pilaf.cs.game.seach.message.GameFoundMessage;
import com.pilaf.cs.game.search.model.UserSearchGame;
import com.pilaf.cs.game.search.repository.CreatedGameRepository;
import com.pilaf.cs.game.search.repository.UserSearchGameRepository;

@Service
@EnableAsync
public class RoomLobbyPlayerFinderService {

	@Autowired
	private UserSearchGameRepository userSearchRepository;

	@Autowired
	private CreatedGameRepository createdGameRepository;

	@Autowired
	private MaximumGamesHolder maxGameHolder;

	@Autowired
	private GameCreator gameCreator;

	@Autowired
	private AfterGameCreatedActionService afterGameCreatedActionService;
	
	private Logger log = Logger.getLogger(RoomLobbyPlayerFinderService.class.getName());

	@Async
	@Transactional
	@Scheduled(fixedDelay = 2000)
	public synchronized CompletableFuture<List<Long>> findPlayersMatches()
			throws InterruptedException, ExecutionException {
		if (canCreateGame(userSearchRepository.count(), createdGameRepository.count(),
				maxGameHolder.getMaximumGames())) {
			Future<GameFoundMessage> futureResult = gameCreator.createNewGame(findPlayersForGame());
			while (!futureResult.isDone()) {
				Thread.sleep(500);
			}
			GameFoundMessage message = futureResult.get();
			afterGameCreatedActionService.performAfterGameCreated(message);
			return CompletableFuture
					.completedFuture(Arrays.asList(new Long[] { message.getPlayer1Id(), message.getPlayer2Id() }));
		}
		return CompletableFuture.completedFuture(Arrays.asList(new Long[] {}));
	}

	private List<UserSearchGame> findPlayersForGame() {
		List<UserSearchGame> returnedList = userSearchRepository.findAll().stream().limit(2).collect(Collectors.toList());
		return returnedList;
	}

	private boolean canCreateGame(long playersSearchingForGame, long gamesCreatedAllready, long maxGamesLimit) {
		boolean canCreate = true;
		if (playersSearchingForGame < 2) {
			canCreate = false;
		} else if (gamesCreatedAllready >= maxGamesLimit) {
			canCreate = false;
		}
		return canCreate;
	}
}
