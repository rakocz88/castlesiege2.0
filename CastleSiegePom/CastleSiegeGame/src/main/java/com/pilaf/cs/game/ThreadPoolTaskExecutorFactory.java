package com.pilaf.cs.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ThreadPoolTaskExecutorFactory {

	@Value("${cs.game.corePoolSize}")
	private int corePoolSize;

	@Value("${cs.game.maxPoolSize}")
	private int maxPoolSize;

	@Value("${cs.game.queueCapacity}")
	private int queueCapacity;

	@Value("${cs.game.allowCoreThreadTimeOut}")
	private boolean allowCoreThreadTimeOut;

	@Value("${cs.game.keepAliveSeconds}")
	private int keepAliveSeconds;

	public ThreadPoolTaskExecutor createThreadPoolTaskExecutorWithDefaultValues() {
		ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
		t.setCorePoolSize(corePoolSize);
		t.setMaxPoolSize(maxPoolSize);
		t.setQueueCapacity(queueCapacity);
		t.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
		t.setKeepAliveSeconds(keepAliveSeconds);
		return t;
	}

}
