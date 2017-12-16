package com.pilaf.cs.game.search.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
public class CreatedGame {



	@Id
	@GeneratedValue
	private long id;

	private long player1Id;

	private long player2Id;

	@NotNull
	@NonNull
	private String gameId;

	private boolean gameFinnished;
	
	public CreatedGame(long player1, long player2, String gameId) {
		this.player1Id=player1;
		this.player2Id=player2;
		this.gameId=gameId;
	}

}
