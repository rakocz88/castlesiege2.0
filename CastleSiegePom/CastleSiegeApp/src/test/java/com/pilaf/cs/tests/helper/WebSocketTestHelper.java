package com.pilaf.cs.tests.helper;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.pilaf.cs.tests.builder.AbstractWebSocketSecurityTestState;

@Component
public class WebSocketTestHelper {

	@SuppressWarnings("rawtypes")
	private CompletableFuture completableFuture;

	public void connectAndSubscribeToChannel(String websocketURL, String channelName,
			AbstractWebSocketSecurityTestState instance)
			throws InterruptedException, ExecutionException, TimeoutException {
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
			stompSession.subscribe(channelName, new CSStompFrameHandler(completableFuture));
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

}
