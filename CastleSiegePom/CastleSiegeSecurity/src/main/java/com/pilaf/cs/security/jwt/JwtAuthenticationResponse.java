package com.pilaf.cs.security.jwt;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;

	private String token;

	public JwtAuthenticationResponse() {
		super();
	}

	public JwtAuthenticationResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}
