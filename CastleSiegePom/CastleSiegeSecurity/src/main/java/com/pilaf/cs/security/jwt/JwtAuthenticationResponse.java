package com.pilaf.cs.security.jwt;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by stephan on 20.03.16.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;

	private String token;

}
