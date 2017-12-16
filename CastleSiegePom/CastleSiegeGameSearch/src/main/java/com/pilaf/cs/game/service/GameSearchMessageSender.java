package com.pilaf.cs.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.pilaf.cs.game.seach.message.GameFoundMessage;
import com.pilaf.cs.game.search.model.GameStateOld;

@Component
public class GameSearchMessageSender {

	@Autowired
	private SimpMessagingTemplate websocket;

	@Async
	public synchronized void sendMessageToFoundGameChannelChannel(String topic, GameFoundMessage message) {
		GameStateOld gameState = new GameStateOld();
		gameState.setWhosTurn(4);
		websocket.convertAndSend(topic, message);
	}

}
