package com.pilaf.cs.tests.builder;

import java.util.concurrent.CompletableFuture;

import org.springframework.messaging.simp.stomp.StompSession;

public abstract class AbstractWebSocketSecurityTestState extends AbstractWithUserTestState {

	private StompSession stompSession;

	private CompletableFuture completableFuture;

	private boolean authenticationFailure;


	

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

	
	public boolean isAuthenticationFailure() {
		return authenticationFailure;
	}

	public void setAuthenticationFailure(boolean authenticationFailure) {
		this.authenticationFailure = authenticationFailure;
	}

}
