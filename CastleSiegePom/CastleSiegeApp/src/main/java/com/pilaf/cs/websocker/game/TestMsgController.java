package com.pilaf.cs.websocker.game;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.pilaf.cs.game.model.GameState;
import com.pilaf.cs.game.model.Move;
import com.pilaf.cs.game.service.GameService;

@Controller
public class TestMsgController {
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

    @MessageMapping("/move/{uuid}")
    @SendTo("/topic/move/{uuid}")
    public GameState makeMove(@DestinationVariable String uuid, Move move) {
        GameState gameState = gameService.move(UUID.fromString(uuid), move);

        return gameState;
    }
}