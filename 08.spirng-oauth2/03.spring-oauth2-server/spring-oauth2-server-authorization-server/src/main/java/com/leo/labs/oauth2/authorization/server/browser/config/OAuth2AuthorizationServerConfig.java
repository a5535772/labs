package com.leo.labs.oauth2.authorization.server.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService simpleInternalMemoryUserDetailsService;
	@Autowired
	private TokenStore redisTokenStore;
	@Autowired
	private JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices;
	@Autowired
	private ClientDetailsService clientDetails;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetails);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
		endpoints.tokenStore(redisTokenStore);
		endpoints.userDetailsService(simpleInternalMemoryUserDetailsService);// 要使用refresh_token的话，需要额外配置userDetailsService
		endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices);

	}
}
