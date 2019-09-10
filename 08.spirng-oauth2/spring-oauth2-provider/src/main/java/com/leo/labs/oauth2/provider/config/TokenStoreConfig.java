package com.leo.labs.oauth2.provider.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class TokenStoreConfig {
	@Autowired
	private DataSource dataSource;

	/**
	 * 用户验证信息的保存策略，可以存储在内存中，关系型数据库中，redis中
	 */
	@Bean
	public TokenStore jdbcTokenStore() {
		/**
		 * return new RedisTokenStore(connectionFactory);
		 * <p/>
		 * return new InMemoryTokenStore();
		 **/
		return new JdbcTokenStore(dataSource);
	}
}
