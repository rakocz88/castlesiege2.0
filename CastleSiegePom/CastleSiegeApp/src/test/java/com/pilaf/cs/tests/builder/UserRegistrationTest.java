package com.pilaf.cs.tests.builder;

import java.util.ArrayList;
import java.util.List;

import com.pilaf.cs.users.model.User;

public class UserRegistrationTest {

	private String authorizationToken;
	private int currentHttpStatus;
	private User currentUser;
	private static UserRegistrationTest instance = null;

	private UserRegistrationTest() {
		super();
	}

	public static UserRegistrationTest getInstance() {
		if (instance == null) {
			instance = new UserRegistrationTest();
		}
		return instance;
	}

	public static UserRegistrationTest resetData() {
		instance = new UserRegistrationTest();
		return instance;
	}

	public String getAuthorizationToken() {
		return authorizationToken;
	}

	public UserRegistrationTest setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
		return this;
	}

	public int getCurrentHttpStatus() {
		return currentHttpStatus;
	}

	public UserRegistrationTest setCurrentHttpStatus(int currentHttpStatus) {
		this.currentHttpStatus = currentHttpStatus;
		return this;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public UserRegistrationTest setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
		return this;
	}

}
