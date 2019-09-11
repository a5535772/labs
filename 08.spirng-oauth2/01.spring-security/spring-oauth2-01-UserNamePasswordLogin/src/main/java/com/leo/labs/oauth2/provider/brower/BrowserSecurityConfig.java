package com.leo.labs.oauth2.provider.brower;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic().and().authorizeRequests().anyRequest().authenticated();
		http.formLogin().and().authorizeRequests().anyRequest().authenticated();
	}

}
