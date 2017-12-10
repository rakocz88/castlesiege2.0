package com.pilaf.cs.game.search.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class CreatedGame {

	@Id
	@GeneratedValue
	private long id;

	private long player1Id;

	private long player2Id;

	@NotNull
	private String gameId;

	private boolean gameFinnished;

	public CreatedGame() {
		super();
	}

	public CreatedGame(long player1Id, long player2Id, String gameId) {
		super();
		this.player1Id = player1Id;
		this.player2Id = player2Id;
		this.gameId = gameId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPlayer1Id() {
		return player1Id;
	}

	public void setPlayer1Id(long player1Id) {
		this.player1Id = player1Id;
	}

	public long getPlayer2Id() {
		return player2Id;
	}

	public void setPlayer2Id(long player2Id) {
		this.player2Id = player2Id;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public boolean isGameFinnished() {
		return gameFinnished;
	}

	public void setGameFinnished(boolean gameFinnished) {
		this.gameFinnished = gameFinnished;
	}

}
