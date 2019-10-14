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

/**
 * 
 * <一句话功能简述>
 * @Title: SimpleInternalMemoryUserDetailsService.java
 * @Description: <功能详细描述>
 * @author  Leo Zhang
 * @date 2019年10月14日下午1:29:09
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
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
		simpleUserDetails.setPassword(passwordEncoder.encode("123456"));//hard code for test
		return simpleUserDetails;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		simpleUserDetailsMap.put("zhangsan", buildSimpleUserDetails("zhangsan"));
		simpleUserDetailsMap.put("13402037335", buildSimpleUserDetails("lisi"));

	}

}
