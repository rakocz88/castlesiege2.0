package com.pilaf.cs.game.cache.api;

import com.pilaf.cs.common.api.Cacheable;

public interface GameCache<T extends Cacheable> {
	
	void updateCache(String uuid, T cachable);
	
	void removeFromCache(String uuid);
	
	T getFromCache(String uuid);
	

}
