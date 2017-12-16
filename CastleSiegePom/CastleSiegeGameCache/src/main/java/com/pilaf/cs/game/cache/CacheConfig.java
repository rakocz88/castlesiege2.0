package com.pilaf.cs.game.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.pilaf.cs.common.api.GameState;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	@SuppressWarnings("rawtypes")
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		return new RedisCacheManager(redisTemplate);
	}

	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.afterPropertiesSet();
		return jedisConnectionFactory;
	}

	@Bean
	@SuppressWarnings("rawtypes")
	public RedisTemplate redisTemplate(RedisConnectionFactory redisCF) {
		RedisTemplate<String, GameState> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisCF);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
