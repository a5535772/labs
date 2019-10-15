package com.leo.labs.oauth2.resource.server.browser.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.leo.labs.oauth2.core.userdetails.SimpleInternalMemoryUserDetailsService;

@Configuration
public class SecuritySupportConfig {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Bean // 使用JdbcClientDetailsService存储租户信息
	public ClientDetailsService clientDetails() {
		return new JdbcClientDetailsService(dataSource);
	}

	@Bean // 声明TokenStore实现，使用redis存储授权的token
	public TokenStore redisTokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}

	@Bean
	public UserDetailsService simpleInternalMemoryUserDetailsService() {
		return new SimpleInternalMemoryUserDetailsService();
	}

}
