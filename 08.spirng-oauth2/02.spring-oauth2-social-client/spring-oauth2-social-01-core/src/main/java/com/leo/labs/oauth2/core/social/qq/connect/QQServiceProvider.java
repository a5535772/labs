/**
 * 
 */
package com.leo.labs.oauth2.core.social.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.leo.labs.oauth2.core.social.qq.api.QQApi;
import com.leo.labs.oauth2.core.social.qq.api.QQImpl;

/**
 * 
 * <一句话功能简述>
 * @Title: QQServiceProvider.java
 * @Description: <功能详细描述>
 * @author  Leo Zhang
 * @date 2019年9月23日下午2:18:45
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApi> {

	private String appId;
	
	/**引导用户,进行授权的页面*/
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
	/**根据授权码,获取token的地址*/
	private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
	
	public QQServiceProvider(String appId, String appSecret) {
//		super(new OAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId;
	}
	
	@Override
	public QQApi getApi(String accessToken) {
		return new QQImpl(accessToken, appId);
	}

}
