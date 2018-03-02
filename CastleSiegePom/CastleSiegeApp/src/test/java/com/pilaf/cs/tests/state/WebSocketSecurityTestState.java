package com.pilaf.cs.tests.state;

public class WebSocketSecurityTestState extends AbstractWebSocketSecurityTestState {
	private static WebSocketSecurityTestState instance = null;

	public static AbstractWebSocketSecurityTestState getInstance() {
		if (instance == null) {
			instance = new WebSocketSecurityTestState();
		}
		return instance;
	}

	public static AbstractWebSocketSecurityTestState resetData() {
		instance = new WebSocketSecurityTestState();
		return instance;
	}

	public static void reset() {
		instance = new WebSocketSecurityTestState();
	}

}
