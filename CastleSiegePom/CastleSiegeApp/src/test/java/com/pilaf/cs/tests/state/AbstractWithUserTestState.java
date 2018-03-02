package com.pilaf.cs.tests.state;

import com.pilaf.cs.users.model.User;

public abstract class AbstractWithUserTestState {

	private String authorizationToken;
	private User returnedUser;
	private int currentHttpStatus;

	public String getAuthorizationToken() {
		return authorizationToken;
	}

	public AbstractWithUserTestState setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
		return this;
	}

	public User getReturnedUser() {
		return returnedUser;
	}

	public AbstractWithUserTestState setReturnedUser(User returnedUser) {
		this.returnedUser = returnedUser;
		return this;
	}

	public int getCurrentHttpStatus() {
		return currentHttpStatus;
	}

	public AbstractWithUserTestState setCurrentHttpStatus(int currentHttpStatus) {
		this.currentHttpStatus = currentHttpStatus;
		return this;
	}

}
