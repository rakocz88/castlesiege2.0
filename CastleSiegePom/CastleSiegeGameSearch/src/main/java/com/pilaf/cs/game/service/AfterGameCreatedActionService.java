package com.pilaf.cs.game.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.pilaf.cs.game.seach.message.GameFoundMessage;
import com.pilaf.cs.game.search.repository.UserSearchGameRepository;

@Component
public class AfterGameCreatedActionService {

	@Autowired
	private GameSearchMessageSender gameSearchMessageSender;

	@Autowired
	private UserSearchGameRepository userSearchRepository;

	private Logger log = Logger.getLogger(AfterGameCreatedActionService.class.getName());

	@Async
	public void performAfterGameCreated(GameFoundMessage message) {
		log.info("Try to remove player search " + message.getPlayer1Id() + "  and " + message.getPlayer2Id());
		removePlayersFromLobby(message.getPlayer1Id(), message.getPlayer2Id());
		gameSearchMessageSender.sendMessageToFoundGameChannelChannel("/topic/foundGames", message);
	}

	private void removePlayersFromLobby(long userId1, long userId2) {
		try {
			userSearchRepository.deleteByUserId(userId1);
			userSearchRepository.deleteByUserId(userId2);
			log.info("Successfull removed players " + userId1 + " and " + userId2 + " from search");
		} catch (Exception ex) {
			log.info("The players found is "+userSearchRepository.findOne(userId1));
			log.info("The players found is "+userSearchRepository.findOne(userId2));
			log.severe("Error in removing players");
		}
	}

}
