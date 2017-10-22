package com.pilaf.cs.rest.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pilaf.cs.users.UserBeanConfiguration;
import com.pilaf.cs.users.biz.UserBiz;
import com.pilaf.cs.users.model.User;

@RestController
@RequestMapping("users")
@Import(UserBeanConfiguration.class)
public class UserRest {

	private final UserBiz userBiz;

	@Autowired
	public UserRest(UserBiz userBiz) {
		this.userBiz = userBiz;
	}

	@RequestMapping(value = "{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User getByName(@PathVariable("name") String name) {
		return userBiz.getByName(name);
	}
	
	 @PreAuthorize("hasRole('ADMIN')")
	 @RequestMapping(value = "all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 public List<User> getAllUsers(){
		 return userBiz.getAll();
	 }
}
