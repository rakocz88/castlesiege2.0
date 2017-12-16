package com.pilaf.cs.game.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.pilaf.cs.common.api.models.GameState;
import com.pilaf.cs.game.cache.api.GameCache;

@Component
public class GameCacheImpl implements GameCache<GameState> {

	private static final String GAME_STATE_KEY = "GameState";

	@Autowired
	private RedisTemplate<String, GameState> redisTemplate;

	@Override
	public void updateCache(String uuid, GameState cachable) {
		redisTemplate.opsForHash().put(GAME_STATE_KEY, uuid, cachable);
	}

	@Override
	public void removeFromCache(String uuid) {
		redisTemplate.opsForHash().delete(GAME_STATE_KEY, uuid);

	}

	@Override
	public GameState getFromCache(String uuid) {
		return (GameState) redisTemplate.opsForHash().get(GAME_STATE_KEY, uuid);
	}

}
