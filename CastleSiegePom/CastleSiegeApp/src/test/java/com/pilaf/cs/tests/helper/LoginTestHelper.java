package com.pilaf.cs.tests.helper;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.pilaf.cs.security.JwtAuthenticationRequest;
import com.pilaf.cs.security.JwtAuthenticationResponse;
import com.pilaf.cs.tests.builder.AbstractWithUserTestState;
import com.pilaf.cs.users.model.User;

@Component
public class LoginTestHelper {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private static final String AUTHORIZATION_TOKEN_FORMAT = "Bearer %s";

	public void logInWithUser(String userName, String password, AbstractWithUserTestState instance, int port) {
		User user = new User(userName, password);
		instance.setReturnedUser(user);
		String url = String.format(RestEndpoints.LOGIN_ENDPOINT, port);
		JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest(
				instance.getReturnedUser().getUsername(), instance.getReturnedUser().getPassword());
		ResponseEntity<JwtAuthenticationResponse> response = testRestTemplate.postForEntity(url, authenticationRequest,
				JwtAuthenticationResponse.class);
		instance.setAuthorizationToken(response.getBody().getToken());
		instance.setCurrentHttpStatus(response.getStatusCode().value());
	}

	public MultiValueMap<String, String> getHeaders(String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.put("Accept", Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
		headers.put("Content-Type", Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
		headers.put("Authorization", Collections.singletonList(String.format(AUTHORIZATION_TOKEN_FORMAT, token)));
		return headers;
	}

}
