package com.pilaf.cs.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pilaf.cs.users.biz.UserBiz;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserBiz userBiz;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new UserAuth(userBiz.getByName(username));
	}

}
