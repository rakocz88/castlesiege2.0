package com.pilaf.cs.game.seach.message;

public class GameFoundMessage {

	private String gameUUID;

	private long player1Id;

	private long player2Id;

	public GameFoundMessage(String gameUUID, long player1Id, long player2Id) {
		super();
		this.gameUUID = gameUUID;
		this.player1Id = player1Id;
		this.player2Id = player2Id;
	}

	public String getGameUUID() {
		return gameUUID;
	}

	public void setGameUUID(String gameUUID) {
		this.gameUUID = gameUUID;
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

}
