package com.pilaf.cs.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
public class GameState implements Cacheable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -30442168360010916L;

	@NonNull
	private String gameUUID;

	private String description;

}
