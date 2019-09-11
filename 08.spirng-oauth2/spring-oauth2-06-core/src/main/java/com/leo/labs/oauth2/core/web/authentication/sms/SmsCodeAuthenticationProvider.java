package com.leo.labs.oauth2.core.web.authentication.sms;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 
 * <一句话功能简述>
 * 
 * @Title: SMSCodeAuthenticationProvider.java
 * @Description: <功能详细描述>
 * @author Leo Zhang
 * @date 2019年9月11日下午5:04:36
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
		UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
		if (user == null) {
			throw new InternalAuthenticationServiceException("手机号不存在");
		}
		SmsCodeAuthenticationToken resultAuthenticationToken = new SmsCodeAuthenticationToken(user,
				user.getAuthorities());
		resultAuthenticationToken.setDetails(authenticationToken.getDetails());
		return resultAuthenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
