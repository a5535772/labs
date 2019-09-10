package com.leo.labs.oauth2.provider.web.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.leo.labs.oauth2.provider.web.dto.SimpleUserDetails;

public class SimpleInternalMemoryUserDetailsService implements UserDetailsService, InitializingBean {
	private Map<String, SimpleUserDetails> simpleUserDetailsMap = new ConcurrentHashMap<>();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		simpleUserDetailsMap.put("zhangsan", buildSimpleUserDetails("zhangsan"));

	}

	private SimpleUserDetails buildSimpleUserDetails(String userName) {
		SimpleUserDetails simpleUserDetails = new SimpleUserDetails();
		simpleUserDetails.setUserName(userName);
		simpleUserDetails.setPassword(userName);
		return simpleUserDetails;
	}

}
