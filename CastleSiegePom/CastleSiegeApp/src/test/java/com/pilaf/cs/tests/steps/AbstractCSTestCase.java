package com.pilaf.cs.tests.steps;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.pilaf.cs.game.model.GameState;
import com.pilaf.cs.security.JwtAuthenticationRequest;
import com.pilaf.cs.security.JwtAuthenticationResponse;
import com.pilaf.cs.tests.SpringIntegrationTest;
import com.pilaf.cs.tests.builder.AbstractWithUserTest;
import com.pilaf.cs.tests.builder.WebSocketSecurityTest;
import com.pilaf.cs.users.model.User;

public abstract class AbstractCSTestCase extends SpringIntegrationTest implements RestEndpoints {

	protected TestRestTemplate restTemplate = new TestRestTemplate(new RestTemplate());

	@SuppressWarnings("rawtypes")
	private CompletableFuture completableFuture;

	protected void logInWithUser(String userName, String password, AbstractWithUserTest instance) {
		User user = new User(userName, password);
		instance.setReturnedUser(user);
		String url = String.format(LOGIN_ENDPOINT, port);
		JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest(
				instance.getReturnedUser().getUsername(), instance.getReturnedUser().getPassword());
		ResponseEntity<JwtAuthenticationResponse> response = restTemplate.postForEntity(url, authenticationRequest,
				JwtAuthenticationResponse.class);
		instance.setAuthorizationToken(response.getBody().getToken());
		instance.setCurrentHttpStatus(response.getStatusCode().value());
	}

	protected void connectAndSubscribeToChannel(String websocketURL, String channelName, WebSocketSecurityTest instance)
			throws InterruptedException, ExecutionException, TimeoutException {
		// String uuid = UUID.randomUUID().toString();
		completableFuture = new CompletableFuture<>();
		instance.setCompletableFuture(completableFuture);
		WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
		StompHeaders stompHeaders = new StompHeaders();
		stompHeaders.add("AuthorizationWS", instance.getAuthorizationToken());
		try {
			StompSession stompSession = stompClient
					.connect(websocketURL, headers, stompHeaders, new StompSessionHandlerAdapter() {
					}).get(5, SECONDS);
			stompSession.subscribe(channelName, new CreateGameStompFrameHandler());
			instance.setStompSession(stompSession);
		} catch (TimeoutException ex) {
			instance.setAuthenticationFailure(true);
		}
	}

	private List<Transport> createTransportClient() {
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		return transports;
	}

	private class CreateGameStompFrameHandler implements StompFrameHandler {
		@Override
		public Type getPayloadType(StompHeaders stompHeaders) {
			System.out.println(stompHeaders.toString());
			return GameState.class;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void handleFrame(StompHeaders stompHeaders, Object o) {
			System.out.println((GameState) o);
			completableFuture.complete((GameState) o);
		}
	}
}
