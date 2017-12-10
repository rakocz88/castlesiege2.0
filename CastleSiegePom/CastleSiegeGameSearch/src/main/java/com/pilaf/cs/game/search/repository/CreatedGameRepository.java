package com.pilaf.cs.game.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pilaf.cs.game.search.model.CreatedGame;

public interface CreatedGameRepository extends JpaRepository<CreatedGame, Long> {

	CreatedGame findByGameId(String gameId);


}
