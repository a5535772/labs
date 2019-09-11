package com.leo.labs.oauth2.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * <一句话功能简述>
 * @Title: SecurityProperties.java
 * @Description: <功能详细描述>
 * @author  Leo Zhang
 * @date 2019年9月11日下午1:29:25
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = "labs.security.properties")
public class SecurityProperties {
	
	BrowserProperties  browser =new BrowserProperties();

	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}
	
}
