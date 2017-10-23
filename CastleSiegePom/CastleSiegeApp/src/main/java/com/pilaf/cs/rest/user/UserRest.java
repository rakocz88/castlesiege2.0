package com.pilaf.cs.rest.user;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pilaf.cs.rest.AbstractRestController;
import com.pilaf.cs.security.JwtTokenUtil;
import com.pilaf.cs.security.UserAuth;
import com.pilaf.cs.users.UserBeanConfiguration;
import com.pilaf.cs.users.biz.UserBiz;
import com.pilaf.cs.users.model.AuthorityName;
import com.pilaf.cs.users.model.User;

@RestController
@RequestMapping("users")
@Import(UserBeanConfiguration.class)
public class UserRest extends AbstractRestController{

	private final UserBiz userBiz;

	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public UserRest(UserBiz userBiz, JwtTokenUtil jwtTokenUtil) {
		this.userBiz = userBiz;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User getByName(HttpServletRequest request,  HttpServletResponse response, @PathVariable("name") String name) throws IOException {
		// TODO FILIP move function to validator
		UserAuth user = jwtTokenUtil.getUserFromRequest(request);
		boolean isAdmin = !user.getAuthorities().stream()
				.filter(auth -> auth.getAuthority().equals(AuthorityName.ROLE_ADMIN.name()))
				.collect(Collectors.toList()).isEmpty();
		if ((!user.getUsername().equals(name)) && !isAdmin) {
			throw new HTTPException(HttpStatus.FORBIDDEN.value());
		}
		return userBiz.getByName(name);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		return userBiz.getAll();
	}
}
