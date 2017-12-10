package com.pilaf.cs.game.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pilaf.cs.game.search.model.GameState;
import com.pilaf.cs.game.search.model.Move;
import com.pilaf.cs.game.search.model.UserSearchGame;
import com.pilaf.cs.game.search.repository.UserSearchGameRepository;


@Service
public class GameService {
	
	@Autowired
	private UserSearchGameRepository userSearchGameRepository;
	
    public GameState createGame(UUID uuid) {
        return new GameState(uuid, new int[][] {{1, 1}, {1, 1}}, 0, false);
    }

    public GameState move(UUID uuid, Move move) {
        return new GameState(uuid, new int[][] {{2, 2}, {2, 2}}, 1, false);
    }
    
    public void addPlayerToSearchGameLobby(long playerId){
    	userSearchGameRepository.save(new UserSearchGame(playerId));
    }
}
