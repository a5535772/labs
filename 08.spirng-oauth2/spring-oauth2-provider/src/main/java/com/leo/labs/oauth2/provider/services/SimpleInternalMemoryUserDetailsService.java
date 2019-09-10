package com.leo.labs.oauth2.provider.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.leo.labs.oauth2.provider.dto.SimpleUserDetails;

public class SimpleInternalMemoryUserDetailsService implements UserDetailsService {
	private Map<String, SimpleUserDetails> simpleUserDetailsMap = new ConcurrentHashMap<>();

	public SimpleInternalMemoryUserDetailsService() {
		simpleUserDetailsMap.put("zhangsan", buildSimpleUserDetails("zhangsan"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = simpleUserDetailsMap.get(username);

		if (userDetails == null)
			throw new UsernameNotFoundException("Unknown user");
		
		return userDetails;
	}

	private SimpleUserDetails buildSimpleUserDetails(String userName) {
		SimpleUserDetails simpleUserDetails = new SimpleUserDetails();
		simpleUserDetails.setUserName(userName);
		simpleUserDetails.setPassword("123456");
		return simpleUserDetails;
	}

}
