package com.pilaf.cs.tests.builder;

import com.pilaf.cs.users.model.User;

public abstract class AbstractWithUserTest {

	private String authorizationToken;
	private User returnedUser;
	private int currentHttpStatus;

	public String getAuthorizationToken() {
		return authorizationToken;
	}

	public AbstractWithUserTest setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
		return this;
	}

	public User getReturnedUser() {
		return returnedUser;
	}

	public AbstractWithUserTest setReturnedUser(User returnedUser) {
		this.returnedUser = returnedUser;
		return this;
	}

	public int getCurrentHttpStatus() {
		return currentHttpStatus;
	}

	public AbstractWithUserTest setCurrentHttpStatus(int currentHttpStatus) {
		this.currentHttpStatus = currentHttpStatus;
		return this;
	}

}
