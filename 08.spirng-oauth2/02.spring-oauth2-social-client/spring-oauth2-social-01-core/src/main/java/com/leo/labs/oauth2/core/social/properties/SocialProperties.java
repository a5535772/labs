package com.leo.labs.oauth2.core.social.properties;

public class SocialProperties {
	QQProperties qq = new QQProperties();
	WeixinProperties weixin = new WeixinProperties();

	private String filterProcessUrl = "/qqLogin";

	public QQProperties getQq() {
		return qq;
	}

	public void setQq(QQProperties qq) {
		this.qq = qq;
	}

	public String getFilterProcessUrl() {
		return filterProcessUrl;
	}

	public void setFilterProcessUrl(String filterProcessUrl) {
		this.filterProcessUrl = filterProcessUrl;
	}

	

	public WeixinProperties getWeixin() {
		return weixin;
	}

	public void setWeixin(WeixinProperties weixin) {
		this.weixin = weixin;
	}

	@Override
	public String toString() {
		return "SocialProperties [qq=" + qq + ", weixin=" + weixin + ", filterProcessUrl=" + filterProcessUrl + "]";
	}

}
