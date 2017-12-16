package com.pilaf.cs.game.search.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GameStateOld implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7342250257652646594L;
	private UUID gameUUID;
	private int[][] board = new int[2][2];
	private int whosTurn;
	private boolean gameOver;

}
