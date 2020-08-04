package com.leo.labs.es.high.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leo.labs.es.high.es.HighLevelClientPool;
import com.leo.labs.es.high.es.RealHighLevelClientPool;

@Configuration
public class Config {
	@Bean
	public HighLevelClientPool buildHighLevelClientPool() {
		return new RealHighLevelClientPool();
	}
}
