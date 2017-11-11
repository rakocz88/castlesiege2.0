package com.pilaf.cs.security.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.pilaf.cs.security.JwtTokenUtil;
import com.pilaf.cs.users.model.User;
import com.pilaf.cs.users.repository.UserRepository;

@Component
public class WebSocketAuthenticatorService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;

	public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String token)
			throws AuthenticationException {
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new AuthenticationCredentialsNotFoundException("Error in authentication.");
		}
//		SecurityContextHolder.getContext().setAuthentication(new UserAuth(user));
		// // Add your own logic for retrieving user in fetchUserFromDb()
		// if (fetchUserFromDb(username, password) == null) {
		// throw new BadCredentialsException("Bad credentials for user " +
		// username);
		// }

		// null credentials, we do not pass the password along to prevent
		// security flaw
		return new UsernamePasswordAuthenticationToken(username, null, null);
	}
}