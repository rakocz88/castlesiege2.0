package com.pilaf.cs.users.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {
	
	public String generateActivationCodeLink(){
		 return UUID.randomUUID().toString();
	}

}
