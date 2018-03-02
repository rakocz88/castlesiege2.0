package com.pilaf.cs.tests.builder;

import java.util.ArrayList;
import java.util.List;

import com.pilaf.cs.users.model.User;

public class UserTestState extends AbstractWithUserTestState {

	private static UserTestState instance = null;
	private List<User> userList = new ArrayList<>();

	private UserTestState() {
		super();
	}

	public static UserTestState getInstance() {
		if (instance == null) {
			instance = new UserTestState();
		}
		return instance;
	}

	public static UserTestState resetData() {
		instance = new UserTestState();
		return instance;
	}

	public List<User> getUserList() {
		return userList;
	}

	public UserTestState setUserList(List<User> userList) {
		this.userList = userList;
		return this;
	}

}
