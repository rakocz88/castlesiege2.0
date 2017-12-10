package com.pilaf.cs.game.search.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.pilaf.cs.game.service.RoomLobbyPlayerFinderService;

@Component
public class RoomLobbyPlayerFinderAsyncRunner implements ApplicationRunner {

	@Autowired
	private RoomLobbyPlayerFinderService roomLobbyPlayerFinderService;
	
	public void run(ApplicationArguments arg0) throws Exception {
		roomLobbyPlayerFinderService.findPlayersMatches();
	}
}
