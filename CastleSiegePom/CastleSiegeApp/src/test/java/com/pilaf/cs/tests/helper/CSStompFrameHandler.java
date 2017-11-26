package com.pilaf.cs.tests.helper;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

public class CSStompFrameHandler implements StompFrameHandler {

	@SuppressWarnings("rawtypes")
	private CompletableFuture completableFuture;

	public CSStompFrameHandler(CompletableFuture completableFuture) {
		super();
		this.completableFuture = completableFuture;
	}

	@Override
	public Type getPayloadType(StompHeaders stompHeaders) {
		return Object.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleFrame(StompHeaders stompHeaders, Object o) {
		completableFuture.complete((Object) o);
	}

}
