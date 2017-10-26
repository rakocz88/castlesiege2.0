package com.pilaf.cs.rest.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pilaf.cs.rest.AbstractRestController;
import com.pilaf.cs.security.JwtTokenUtil;
import com.pilaf.cs.security.UserAuth;
import com.pilaf.cs.users.biz.UserBiz;
import com.pilaf.cs.users.model.User;
import com.pilaf.cs.validator.UserRestValidator;

@RestController
@RequestMapping("users")
public class UserRest extends AbstractRestController{

	private final UserBiz userBiz;

	private final JwtTokenUtil jwtTokenUtil;
	
	private final UserRestValidator userRestValidator;

	@Autowired
	public UserRest(UserBiz userBiz, JwtTokenUtil jwtTokenUtil, UserRestValidator restValidator) {
		this.userBiz = userBiz;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRestValidator = restValidator;
	}

	@RequestMapping(value = "{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User getByName(HttpServletRequest request,  HttpServletResponse response, @PathVariable("name") String name) throws IOException {
		UserAuth user = jwtTokenUtil.getUserFromRequest(request);
		userRestValidator.validateUserIsOwnerOrAdmin(request, user, name);
		return userBiz.getByName(name);
	}
	


	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		return userBiz.getAll();
	}
}
