package com.leo.labs.oauth2.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.leo.labs.oauth2.provider.services.SimpleInternalMemoryUserDetailsService;

@Configuration
public class UserDetailsServiceConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		/**
		 * return new RedisTokenStore(connectionFactory);
		 * <p/>
		 * return new InMemoryTokenStore();
		 **/
		return new SimpleInternalMemoryUserDetailsService();
	}
}
