package com.pilaf.cs.tests.builder;

import java.util.ArrayList;
import java.util.List;

import com.pilaf.cs.users.model.User;

public class UserTest {

	private String authorizationToken;
	private User returnedUser;
	private int currentHttpStatus;
	private static UserTest instance = null;
	private List<User> userList = new ArrayList<>();

	private UserTest() {
		super();
	}

	public static UserTest getInstance() {
		if (instance == null) {
			instance = new UserTest();
		}
		return instance;
	}

	public static UserTest resetData() {
		instance = new UserTest();
		return instance;
	}


	public String getAuthorizationToken() {
		return authorizationToken;
	}

	public UserTest setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
		return this;
	}

	public User getReturnedUser() {
		return returnedUser;
	}

	public UserTest setReturnedUser(User returnedUser) {
		this.returnedUser = returnedUser;
		return this;
	}

	public int getCurrentHttpStatus() {
		return currentHttpStatus;
	}

	public UserTest setCurrentHttpStatus(int currentHttpStatus) {
		this.currentHttpStatus = currentHttpStatus;
		return this;
	}

	public List<User> getUserList() {
		return userList;
	}

	public UserTest setUserList(List<User> userList) {
		this.userList = userList;
		return this;
	}
	
	

}
