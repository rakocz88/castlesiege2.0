package com.pilaf.cs.security;

import com.pilaf.cs.users.model.User;

public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static UserAuth create(User user) {
		return new UserAuth(new User(user.getUsername(), user.getPassword(), user.getAuthorities()));
	}

//	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
//		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
//				.collect(Collectors.toList());
//	}
}
