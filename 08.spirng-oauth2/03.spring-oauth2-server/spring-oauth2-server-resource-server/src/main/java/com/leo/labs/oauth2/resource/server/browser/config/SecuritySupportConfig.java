package com.leo.labs.oauth2.resource.server.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.leo.labs.oauth2.core.support.SecurityConstants;

@Configuration
public class SecuritySupportConfig {
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Bean // 声明TokenStore实现，使用redis存储授权的token
	public TokenStore redisTokenStore() {
		RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
		redisTokenStore.setPrefix(SecurityConstants.REDIS_USER_TOKEN_PREFIX);
		return redisTokenStore;
	}

}
