package com.pilaf.cs.tests.builder;

import java.util.ArrayList;
import java.util.List;

import com.pilaf.cs.users.model.User;

public class UserTest extends AbstractWithUserTest {

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

	public List<User> getUserList() {
		return userList;
	}

	public UserTest setUserList(List<User> userList) {
		this.userList = userList;
		return this;
	}

}
