package com.leo.labs.oauth2.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.leo.labs.oauth2.core.social.properties.SocialProperties;

/**
 * 
 * <一句话功能简述>
 * 
 * @Title: SecurityProperties.java
 * @Description: <功能详细描述>
 * @author Leo Zhang
 * @date 2019年9月11日下午1:29:25
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@ConfigurationProperties(prefix = "labs.security.properties")
public class SecurityProperties {

	BrowserProperties browser = new BrowserProperties();

	SocialProperties social = new SocialProperties();

	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public SocialProperties getSocial() {
		return social;
	}

	public void setSocial(SocialProperties social) {
		this.social = social;
	}

}
