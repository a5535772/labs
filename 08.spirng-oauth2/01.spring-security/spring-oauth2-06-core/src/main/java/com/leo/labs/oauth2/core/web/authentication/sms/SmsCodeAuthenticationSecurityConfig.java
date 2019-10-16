package com.leo.labs.oauth2.core.web.authentication.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SmsCodeAuthenticationSecurityConfig
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authenticationProvider(buildPorvider()).addFilterAfter(buildFilter(http), // 加入到AuthenticationManager的管辖范围
				UsernamePasswordAuthenticationFilter.class);// 把filter加入到filterchan当中去，并处于UsernamePasswordAuthenticationFilter之后

	}

	private SmsCodeAuthenticationProvider buildPorvider() {
		SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	private SmsCodeAuthenticationFilter buildFilter(HttpSecurity http) {
		SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
		filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(authenticationFailureHandler);
		return filter;
	}

}
