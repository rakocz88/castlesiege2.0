package com.pilaf.cs.users.validator;


import javax.validation.ValidationException;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
	
	public void validatePassword(String password){
		 String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		 if (password.matches(pattern)){
			 throw new ValidationException("Wrong password format");
		 }
		
	}

}
