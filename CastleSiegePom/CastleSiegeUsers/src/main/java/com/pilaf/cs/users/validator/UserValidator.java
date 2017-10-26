package com.pilaf.cs.users.validator;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pilaf.cs.users.model.User;
import com.pilaf.cs.users.repository.UserRepository;

@Component
public class UserValidator {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordValidator passwordValidator;

	public void validateRegisteredUser(User user) {
		checkMandatoryFields(user);
		validateUserNameDoesentExistInDB(user.getUsername());
		validateEmailDoesentExistInDB(user.getUsername());
		passwordValidator.validatePassword(user.getPassword());
	}

	private void validateEmailDoesentExistInDB(String username) {
		if (userRepository.findByUsername(username) != null) {
			throw new ValidationException("Username allready exist");
		}
	}

	private void validateUserNameDoesentExistInDB(String mail) {
		if (userRepository.findByEmail(mail) != null) {
			throw new ValidationException("Mail for user allready exists.");
		}

	}

	private void checkMandatoryFields(User user) {
		checkFieldIsMandatory(user.getFirstname());
		checkFieldIsMandatory(user.getLastname());
		checkFieldIsMandatory(user.getEmail());
		checkFieldIsMandatory(user.getPassword());
	}

	private void checkFieldIsMandatory(String field) {
		if (field == null || field.isEmpty()) {
			throw new ValidationException(String.format("%s should not be empty", field) );
		}

	}

}
