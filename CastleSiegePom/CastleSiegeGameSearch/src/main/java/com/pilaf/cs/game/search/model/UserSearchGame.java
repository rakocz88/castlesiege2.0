package com.pilaf.cs.game.search.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class UserSearchGame {

	@Id
	@GeneratedValue
	private Long id;

	private long userId;

	public UserSearchGame(long userId) {
		super();
		this.userId = userId;
	}
}
