package com.pilaf.cs.game.seach.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GameFoundMessage {

	private String gameUUID;

	private long player1Id;

	private long player2Id;

}
