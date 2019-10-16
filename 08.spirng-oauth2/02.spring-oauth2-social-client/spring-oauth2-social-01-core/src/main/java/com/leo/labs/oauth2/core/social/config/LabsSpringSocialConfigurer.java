package com.leo.labs.oauth2.core.social.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class LabsSpringSocialConfigurer extends SpringSocialConfigurer{
	
	private String filterProcessUrl;
	
	public LabsSpringSocialConfigurer(String filterProcessUrl) {
		super();
		this.filterProcessUrl = filterProcessUrl;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter=(SocialAuthenticationFilter)object;
		filter.setFilterProcessesUrl(filterProcessUrl);//默认/auth
		return (T) filter;
	}
	
}
