package com.pilaf.cs.tests.state;

public class UserRegistrationTestState extends AbstractWithUserTestState{

	private static UserRegistrationTestState instance = null;

	private UserRegistrationTestState() {
		super();
	}

	public static UserRegistrationTestState getInstance() {
		if (instance == null) {
			instance = new UserRegistrationTestState();
		}
		return instance;
	}

	public static UserRegistrationTestState resetData() {
		instance = new UserRegistrationTestState();
		return instance;
	}


}
