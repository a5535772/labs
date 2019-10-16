package com.leo.labs.oauth2.authorization.server.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.leo.labs.oauth2.authorization.server.browser.filters.pre.LabUserTokenAuthorizeFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LabUserTokenAuthorizeFilter labUserTokenAuthorizeFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin();// 表单登录

		http.authorizeRequests()// 请求
				.antMatchers("/oauth/authorize", "/oauth/abc", "/hello").permitAll()// 允许匿名访问
				.anyRequest().authenticated();// 除上面外的所有请求全部需要鉴权认证

		http.csrf().disable()// 关闭csrf防跨站请求
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 基于token，所以不需要session
		;
		http.addFilterBefore(labUserTokenAuthorizeFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
