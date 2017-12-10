package com.pilaf.cs.game.service;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.stereotype.Component;

import com.pilaf.cs.game.search.model.UserSearchGame;

@Component
public class CreateGameValidator {

	public void validateBeforeGameCreation(List<UserSearchGame> usersFoundForGame) {
		if (usersFoundForGame.size() != 2) {
			throw new ValidationException("Wrong amount of players trying to start the game");
		}
	}

}
