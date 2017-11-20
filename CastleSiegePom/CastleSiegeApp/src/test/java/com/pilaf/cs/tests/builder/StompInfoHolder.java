package com.pilaf.cs.tests.builder;

import java.util.concurrent.CompletableFuture;

import org.springframework.messaging.simp.stomp.StompSession;

public class StompInfoHolder {

	private StompSession stompSession;

	private CompletableFuture completableFuture;
	
	public StompInfoHolder(StompSession stompSession, CompletableFuture completableFuture) {
		super();
		this.stompSession = stompSession;
		this.completableFuture = completableFuture;
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

}
