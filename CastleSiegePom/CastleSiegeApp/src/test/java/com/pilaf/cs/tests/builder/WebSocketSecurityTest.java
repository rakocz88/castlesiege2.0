package com.pilaf.cs.tests.builder;

import java.util.concurrent.CompletableFuture;

import org.springframework.messaging.simp.stomp.StompSession;

public class WebSocketSecurityTest extends AbstractWithUserTest {
	private static WebSocketSecurityTest instance = null;

	private StompSession stompSession;

	private CompletableFuture completableFuture;

	private boolean authenticationFailure;

	private WebSocketSecurityTest() {
		super();
	}

	public static WebSocketSecurityTest getInstance() {
		if (instance == null) {
			instance = new WebSocketSecurityTest();
		}
		return instance;
	}

	public static WebSocketSecurityTest resetData() {
		instance = new WebSocketSecurityTest();
		return instance;
	}

	public StompSession getStompSession() {
		return stompSession;
	}

	public void setStompSession(StompSession stompSession) {
		this.stompSession = stompSession;
	}

	public CompletableFuture getCompletableFuture() {
		return completableFuture;
	}

	public void setCompletableFuture(CompletableFuture completableFuture) {
		this.completableFuture = completableFuture;
	}

	public static void reset() {
		instance = new WebSocketSecurityTest();
	}

	public boolean isAuthenticationFailure() {
		return authenticationFailure;
	}

	public void setAuthenticationFailure(boolean authenticationFailure) {
		this.authenticationFailure = authenticationFailure;
	}

}
