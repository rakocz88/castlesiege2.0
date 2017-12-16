package com.pilaf.cs.common.api.models;

import com.pilaf.cs.common.api.Cacheable;

public class GameState implements Cacheable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -30442168360010916L;
	private String gameUUID;
	private String description;

	public GameState() {
		super();
	}

	public GameState(String gameUUID) {
		super();
		this.gameUUID = gameUUID;
	}

	public String getGameUUID() {
		return gameUUID;
	}

	public void setGameUUID(String gameUUID) {
		this.gameUUID = gameUUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((gameUUID == null) ? 0 : gameUUID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameState other = (GameState) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (gameUUID == null) {
			if (other.gameUUID != null)
				return false;
		} else if (!gameUUID.equals(other.gameUUID))
			return false;
		return true;
	}

}
