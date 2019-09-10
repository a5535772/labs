package com.leo.labs.oauth2.provider.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private UserDetailsService simpleInternalMemoryUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private DataSource dataSource;

	/**
	 * TokenEndpoint用于处理所有的oauth请求
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.tokenStore(tokenStore); 
		endpoints.userDetailsService(simpleInternalMemoryUserDetailsService);
		endpoints.authenticationManager(authenticationManager);
	}

	/**
	 * 
	 * 我们的应用会给哪些应用发送令牌
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.withClientDetails(clientDetails());// 从数据库中读取
		
		//内存搞起
		clients.inMemory().withClient("democlient").secret("123456").authorizedGrantTypes("authorization_code",
				"client_credentials", "password", "implicit", "refresh_token");
	}

	/**
	 * 使用OAuth2.0自带的JdbcClientDetailsService作为配置，
	 */
	@Bean
	public ClientDetailsService clientDetails() {
		return new JdbcClientDetailsService(dataSource);
	}
	
	/**
	 * 允许表单验证，浏览器直接发送post请求即可获取tocken
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		oauthServer.allowFormAuthenticationForClients();
	}
}
