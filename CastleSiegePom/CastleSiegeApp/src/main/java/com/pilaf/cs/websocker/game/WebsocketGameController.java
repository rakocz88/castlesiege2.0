package com.pilaf.cs.websocker.game;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.pilaf.cs.game.model.GameState;
import com.pilaf.cs.game.service.GameService;

@Controller
public class WebsocketGameController {
    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/create/{uuid}")
    @SendTo("/topic/board/{uuid}")
    public GameState createGame(@DestinationVariable String uuid) {
    	
        GameState gameState = gameService.createGame(UUID.fromString(uuid));

        return gameState;
    }

    @MessageMapping("/move")
    public void makeMove(String move) {
    }
    
    @MessageMapping("/searchGame")
    public void searchGame(Long userId) {
    	 gameService.addPlayerToSearchGameLobby(userId);
    }
    
    @MessageMapping("/duel/{uuid}")
    @SendTo("/topic/duel/{uuid}")
    public String gameChannel(@DestinationVariable String uuid) {
       return "someAction";
    }
}