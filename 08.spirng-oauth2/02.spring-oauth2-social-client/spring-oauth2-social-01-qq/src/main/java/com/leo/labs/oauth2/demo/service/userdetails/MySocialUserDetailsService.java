package com.leo.labs.oauth2.demo.service.userdetails;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MySocialUserDetailsService implements SocialUserDetailsService {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(MySocialUserDetailsService.class);
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public SocialUserDetails loadUserByUserId(String userId) {
		return buildSimpleUserDetails(userId);
	}

	private SocialUserDetails buildSimpleUserDetails(String userId) {
		return new SocialUser(userId, passwordEncoder.encode("123456"), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}
