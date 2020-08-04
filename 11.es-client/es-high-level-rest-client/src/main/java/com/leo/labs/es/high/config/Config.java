package com.leo.labs.es.high.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leo.labs.es.high.es.AlibabaHighLevelClientPool;
import com.leo.labs.es.high.es.HighLevelClientPool;

@Configuration
public class Config {
	@Bean
	public HighLevelClientPool buildHighLevelClientPool() {
		return new AlibabaHighLevelClientPool();
	}
}
