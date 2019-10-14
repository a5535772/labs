package com.leo.labs.oauth2.authorization.server.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
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

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("leo-lab-client-id").secret("leo-lab-client-secret")
//	                .redirectUris("http://localhost:8081/callback") //新增redirect_uri
				.authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token", "password",
						"implicit")
				.scopes("all")
//	                .resourceIds("oauth2-resource")
//	                .accessTokenValiditySeconds(120)
//	                .refreshTokenValiditySeconds(60)
		;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
		endpoints.userDetailsService(simpleInternalMemoryUserDetailsService);// 要使用refresh_token的话，需要额外配置userDetailsService
		endpoints.tokenStore(redisTokenStore);
		endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices);

	}
}
