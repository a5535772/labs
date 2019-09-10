package com.leo.labs.oauth2.provider.web.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.leo.labs.oauth2.provider.web.service.MultipleUserDetailsService;

/**
 * 
 * 授权服务器的声明和配置
 * 
 * @Title: OAuthServerConfiguration.java
 * @Description: 1.声明一个授权服务器只需要继承AuthorizationServerConfigurerAdapter，添加@EnableAuthorizationServer注解。这个注解告诉
 *               Spring 这个应用是 OAuth2 的认证中心。
 *               <p/>
 *               并且复写如下三个方法
 *               <li>ClientDetailsServiceConfigurer:这个configurer定义了客户端细节服务。客户详细信息可以被初始化。</li>
 *               <li>AuthorizationServerSecurityConfigurer:在令牌端点上定义了安全约束。</li>
 *               <li>AuthorizationServerEndpointsConfigurer:定义了授权和令牌端点和令牌服务。</li>
 * @author Leo Zhang
 * @date 2019年9月9日下午1:24:30
 */
@Configuration
@EnableAuthorizationServer
public class OAuthServerConfiguration extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private ClientDetailsService clientDetails;
	@Autowired
	private TokenStore jdbcTokenStore;
	@Autowired
	private MultipleUserDetailsService multipleUserDetailsService;

	/**
	 * 配置客户端详细步骤
	 * ClientDetailsServiceConfigurer类（AuthorizationServerConfigurer类中的一个调用类）
	 * <p/>
	 * 可以用来定义一个基于内存的或者JDBC的客户端信息服务。
	 * <p/>
	 * 客户端对象重要的属性有：
	 * <li>clientId：（必须）客户端id。</li>
	 * <li>secret：（对于可信任的客户端是必须的）客户端的私密信息。</li>
	 * <li>scope：客户端的作用域。如果scope未定义或者为空（默认值），则客户端作用域不受限制。</li>
	 * <li>authorizedGrantTypes：授权给客户端使用的权限类型。默认值为空。</li>
	 * <li>authorities：授权给客户端的权限（Spring普通的安全权限）。</li>
	 * 在运行的应用中，可以通过直接访问隐藏的存储文件（如：JdbcClientDetailsService中用到的数据库表）或者通过实现ClientDetailsManager
	 * 接口（也可以实现ClientDetailsService 接口，或者实现两个接口）来更新客户端信息。
	 * <H1>具体代码如下所示：这里我们将tocken信息存储在mysql中，分布式下你可以存储在redis中，全局共享。</H1>
	 * 可以搞多个实现，根据clientId挑选不同的实现
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetails);
	}

	/**
	 * 这个方法主要的作用用于控制token的端点等信息
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		configJDBC(endpoints);
	}

	private void configJDBC(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
		// 从数据库查请求的路径
		endpoints.tokenStore(jdbcTokenStore);
		// 授权码存储
		// endpoints.authorizationCodeServices(redisAuthenticationCodeServices)
		endpoints.userDetailsService(multipleUserDetailsService);
		// 配置TokenServices参数 可以考虑使用[DefaultTokenServices]，它使用随机值创建令牌
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(endpoints.getTokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
		tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
		tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)); // 30天

		endpoints.tokenServices(tokenServices);

	}

	
	
	private void configJWT(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

//		endpoints.authenticationManager(this.authenticationManager);
//		endpoints.accessTokenConverter(accessTokenConverter());// jwt
//		// 从jwt来数据
//		endpoints.tokenStore(jwtStore());
//		// 授权码存储
//		endpoints.authorizationCodeServices(redisAuthenticationCodeServices);
//
//		endpoints.userDetailsService(multipleUserDetailsService);

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
