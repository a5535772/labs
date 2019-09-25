package com.leo.labs.oauth2.core.social.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

public class WeixinProperties extends SocialProperties {

	private String providerId = "weixin";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	@Override
	public String toString() {
		return "QQProperties [providerId=" + providerId + ", getAppId()=" + getAppId() + ", getAppSecret()="
				+ getAppSecret() + "]";
	}

	

}
