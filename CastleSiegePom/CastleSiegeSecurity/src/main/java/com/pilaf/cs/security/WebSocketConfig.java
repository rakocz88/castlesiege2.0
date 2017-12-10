package com.pilaf.cs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.pilaf.cs.security.websocket.AuthChannelInterceptorAdapter;
import com.pilaf.cs.security.websocket.WebSocketAuthenticatorService;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Autowired
	private WebSocketAuthenticatorService webSocketAuthenticatorService;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
		stompEndpointRegistry.addEndpoint("/game").withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(new AuthChannelInterceptorAdapter(this.webSocketAuthenticatorService));
	}

}
