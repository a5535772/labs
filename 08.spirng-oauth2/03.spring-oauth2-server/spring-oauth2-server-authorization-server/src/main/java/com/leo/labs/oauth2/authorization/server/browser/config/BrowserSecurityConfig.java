package com.leo.labs.oauth2.authorization.server.browser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()// 表单登录
				.and().authorizeRequests().anyRequest().authenticated()//
				.and().csrf().disable()// 这里暂时关闭csrf防跨站请求
		;
	}

}
