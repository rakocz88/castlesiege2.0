package com.pilaf.cs.tests.steps.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.pilaf.cs.tests.SpringIntegrationTest;
import com.pilaf.cs.tests.helper.LoginTestHelper;
import com.pilaf.cs.tests.helper.RestEndpoints;
import com.pilaf.cs.tests.state.UserTestState;
import com.pilaf.cs.users.model.User;

public class AbstractUserModuleTestCase extends SpringIntegrationTest implements RestEndpoints {

	@Autowired
	protected LoginTestHelper loginTestHelper;

	@Autowired
	protected TestRestTemplate testRestTemplate;

	protected void getTheRestEndpoingForInformationForUserWithToken(String userName) {
		MultiValueMap<String, String> headers = loginTestHelper
				.getHeaders(UserTestState.getInstance().getAuthorizationToken());
		String url = String.format(GET_USER_ENDPOINT, port, userName);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<User> response = testRestTemplate.exchange(url, HttpMethod.GET, httpEntity, User.class);
		UserTestState.getInstance().setCurrentHttpStatus(response.getStatusCode().value());
		UserTestState.getInstance().setReturnedUser(response.getBody());
	}

	protected <T> T tryToGetAllUsers(Class<T> clazz) {
		String url = String.format(GET_ALL_USERS_ENDPOINT, port);
		MultiValueMap<String, String> headers = loginTestHelper
				.getHeaders(UserTestState.getInstance().getAuthorizationToken());
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<T> response = testRestTemplate.exchange(url, HttpMethod.GET, httpEntity, clazz);
		UserTestState.getInstance().setCurrentHttpStatus(response.getStatusCode().value());
		return response.getBody();

	}

}
