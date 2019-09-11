package com.leo.labs.oauth2.core.userdetails;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

public class SimpleInternalMemoryUserDetailsService implements UserDetailsService, InitializingBean {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SimpleInternalMemoryUserDetailsService.class);

	private Map<String, SimpleUserDetails> simpleUserDetailsMap = new ConcurrentHashMap<>();

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) {
		logger.info("username is {}", username);
		UserDetails userDetails = simpleUserDetailsMap.get(username);
		if (ObjectUtils.isEmpty(userDetails))
			throw new UsernameNotFoundException("Unknown user");
		logger.info("userDetails is {}", userDetails);
		return userDetails;
	}

	private SimpleUserDetails buildSimpleUserDetails(String userName) {
		SimpleUserDetails simpleUserDetails = new SimpleUserDetails();
		simpleUserDetails.setUsername(userName);
		simpleUserDetails.setPassword(passwordEncoder.encode("123456"));
		return simpleUserDetails;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		simpleUserDetailsMap.put("zhangsan", buildSimpleUserDetails("zhangsan"));

	}

}
