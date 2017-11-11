package com.pilaf.cs.security.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;

public class AuthChannelInterceptorAdapter extends ChannelInterceptorAdapter {
	private static final String USERNAME_HEADER = "login";
	private static final String PASSWORD_HEADER = "passcode";
	private final WebSocketAuthenticatorService webSocketAuthenticatorService;
	


	public AuthChannelInterceptorAdapter(final WebSocketAuthenticatorService webSocketAuthenticatorService) {
		this.webSocketAuthenticatorService = webSocketAuthenticatorService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
		final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (StompCommand.CONNECT == accessor.getCommand()) {

			LinkedMultiValueMap<String, String> tokenMap = ((LinkedMultiValueMap<String, String>) message.getHeaders()
					.get("nativeHeaders"));
			String token = tokenMap.getFirst("AuthorizationWS");

			final UsernamePasswordAuthenticationToken user = webSocketAuthenticatorService
					.getAuthenticatedOrFail(token);

			accessor.setUser(user);
			
		}
		return message;
	}
}