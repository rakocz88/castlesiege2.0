package com.pilaf.cs.rest.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pilaf.cs.rest.AbstractRestController;
import com.pilaf.cs.users.biz.UserBiz;
import com.pilaf.cs.users.model.User;


@RestController
public class RegistrationRest extends AbstractRestController{
	
	@Autowired
	private  UserBiz userBiz;

	
	@RequestMapping(value = "registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public User register(HttpServletRequest request,  HttpServletResponse response, @RequestBody User user) throws IOException {
		return userBiz.registerUser(user);
	}
	
	@RequestMapping(value = "activate/{token}", method = RequestMethod.GET)
	public String activate(HttpServletRequest request,  HttpServletResponse response, @PathVariable("token") String token) throws IOException {
		return userBiz.activateUser(token);
	}
	

}
