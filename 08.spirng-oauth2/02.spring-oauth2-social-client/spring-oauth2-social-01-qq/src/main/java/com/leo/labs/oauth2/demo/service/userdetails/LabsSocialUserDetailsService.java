package com.leo.labs.oauth2.demo.service.userdetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class LabsSocialUserDetailsService implements SocialUserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public SocialUserDetails loadUserByUserId(String userId) {
		/**userId=已从三方授权数据库中得到了我司的userId*/
		return getSocialUserFromDB(userId);
	}

	private SocialUserDetails getSocialUserFromDB(String userId) {
		return new SocialUser(userId, passwordEncoder.encode("123456"), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}
