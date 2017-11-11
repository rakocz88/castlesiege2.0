package com.pilaf.cs.validator;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.pilaf.cs.security.UserAuth;
import com.pilaf.cs.users.model.AuthorityName;

@Component
public class UserRestValidator {

	public void validateUserIsOwnerOrAdmin(HttpServletRequest request, UserAuth user, String requestedUserName) {
		boolean isAdmin = !user.getAuthorities().stream()
				.filter(auth -> auth.getAuthority().equals(AuthorityName.ROLE_ADMIN.name()))
				.collect(Collectors.toList()).isEmpty();
		if ((!user.getUsername().equals(requestedUserName)) && !isAdmin) {
			throw new HTTPException(HttpStatus.FORBIDDEN.value());
		}

	}

}
