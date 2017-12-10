package com.pilaf.cs.game.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pilaf.cs.game.search.model.UserSearchGame;

public interface UserSearchGameRepository extends JpaRepository<UserSearchGame, Long> {
	
	
	UserSearchGame findByUserId(long userId);
	
	@Transactional
	void deleteByUserId(long userId);
	
	

}
