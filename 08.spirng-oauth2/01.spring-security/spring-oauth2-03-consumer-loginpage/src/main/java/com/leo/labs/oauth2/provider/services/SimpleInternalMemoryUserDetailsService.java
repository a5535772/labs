package com.leo.labs.oauth2.provider.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.leo.labs.oauth2.provider.dto.SimpleUserDetails;

@Component
public class SimpleInternalMemoryUserDetailsService implements UserDetailsService,InitializingBean{
	private Map<String, SimpleUserDetails> simpleUserDetailsMap = new ConcurrentHashMap<>();

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username is " + username);
		UserDetails userDetails = simpleUserDetailsMap.get(username);

		if (userDetails == null)
			throw new UsernameNotFoundException("Unknown user");
		System.out.println("userDetails is "+userDetails.toString());
		return userDetails;
	}

	private SimpleUserDetails buildSimpleUserDetails(String userName) {
		SimpleUserDetails simpleUserDetails = new SimpleUserDetails();
		simpleUserDetails.setUserName(userName);
		simpleUserDetails.setPassword(passwordEncoder.encode("123456"));
		return simpleUserDetails;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		simpleUserDetailsMap.put("zhangsan", buildSimpleUserDetails("zhangsan"));
		
	}

}
