package com.pilaf.cs.tests.steps.users;

import com.pilaf.cs.tests.state.AbstractWithUserTestState;
import com.pilaf.cs.users.model.ESUser;

public class UserSearchTestState extends AbstractWithUserTestState {

	private static UserSearchTestState instance = null;

	private ESUser esUser;

	private UserSearchTestState() {
		super();
	}

	public static UserSearchTestState getInstance() {
		if (instance == null) {
			instance = new UserSearchTestState();
		}
		return instance;
	}

	public static UserSearchTestState resetData() {
		instance = new UserSearchTestState();
		return instance;
	}

	public ESUser getEsUser() {
		return esUser;
	}

	public void setEsUser(ESUser esUser) {
		this.esUser = esUser;
	}

}
