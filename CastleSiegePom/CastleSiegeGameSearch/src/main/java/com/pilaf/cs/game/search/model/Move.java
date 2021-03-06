package com.pilaf.cs.game.search.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Move implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9141850868820637079L;
	private int player;
	private int position;

}
