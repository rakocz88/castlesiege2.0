package com.pilaf.cs.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class GameThreadsConfig {

	@Autowired
	public ThreadPoolTaskExecutorFactory threadPoolTaskExecutorFactory;

	@Bean(name = "taskExecutor")
	public TaskExecutor taskExecutor() {
		return threadPoolTaskExecutorFactory.createThreadPoolTaskExecutorWithDefaultValues();
	}


}
