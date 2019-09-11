package com.leo.labs.oauth2.browser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.leo.labs.oauth2.core.userdetails.SimpleInternalMemoryUserDetailsService;
import com.leo.labs.oauth2.core.web.authentication.sms.SmsCodeAuthenticationSecurityConfig;

@Configuration
public class BrowserSecuritySupportConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService browserUserDetailsService() {
		return new SimpleInternalMemoryUserDetailsService();
	}

	@Bean()
	public SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig() {
		return new SmsCodeAuthenticationSecurityConfig();
	}
}
