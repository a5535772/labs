package com.leo.labs.oauth2.resource.server.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Autowired
	private TokenStore redisTokenStore;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		//使用了DefaultTokenServices --> ResourceServerTokenServices
		resources.tokenStore(redisTokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated();
	}
}
