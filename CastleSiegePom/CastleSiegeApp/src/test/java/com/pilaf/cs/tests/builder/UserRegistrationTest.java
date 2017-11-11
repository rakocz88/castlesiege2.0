package com.pilaf.cs.tests.builder;

public class UserRegistrationTest extends AbstractWithUserTest{

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


}
