package com.pilaf.cs.game.search.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserSearchGame {

	@Id
	@GeneratedValue
	private Long id;

	private long userId;

	public UserSearchGame() {
		super();
	}

	public UserSearchGame(long userId) {
		super();
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
