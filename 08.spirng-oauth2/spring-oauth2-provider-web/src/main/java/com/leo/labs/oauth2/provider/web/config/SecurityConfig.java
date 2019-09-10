package com.leo.labs.oauth2.provider.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.leo.labs.oauth2.provider.web.service.CustomerAuthenticationProvider;
import com.leo.labs.oauth2.provider.web.service.MultipleUserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomerAuthenticationProvider provider;
	@Autowired
	private MultipleUserDetailsService userService;

	/**
	 * 用户认证
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
		auth.userDetailsService(userService);
	}

	/**
	 * 1: 请求授权: spring security 使用以下匹配器来匹配请求路劲： antMatchers:使用ant风格的路劲匹配
	 * regexMatchers:使用正则表达式匹配路劲 anyRequest:匹配所有请求路劲
	 * 在匹配了请求路劲后，需要针对当前用户的信息对请求路劲进行安全处理。 2:定制登录行为。 formLogin()方法定制登录操作
	 * loginPage()方法定制登录页面访问地址 defaultSuccessUrl()登录成功后转向的页面 permitAll()
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated()// 其余所有请求都需要认证后才可访问
				.and().formLogin().permitAll();// 允许用户任意访问
		http.csrf().disable();
	}

	/**
	 * 密码模式下必须注入的bean authenticationManagerBean 认证是由 AuthenticationManager 来管理的，
	 * 但是真正进行认证的是 AuthenticationManager 中定义的AuthenticationProvider。
	 * AuthenticationManager 中可以定义有多个 AuthenticationProvider
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
