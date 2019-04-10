package com.xuyao.integration;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.xuyao.integration.dao")
public class XyintegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(XyintegrationApplication.class, args);
	}

	/**
	 * 指定TaskExecutor
	 * @return
	 */
	@Primary
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		return executor;
	}
}
