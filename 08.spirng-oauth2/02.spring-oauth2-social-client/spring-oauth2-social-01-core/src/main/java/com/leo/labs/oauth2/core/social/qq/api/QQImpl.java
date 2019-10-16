/**
 * 
 */
package com.leo.labs.oauth2.core.social.qq.api;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * <一句话功能简述>
 * 
 * @Title: QQImpl.java
 * @Description: <非单例的实现，多实例对象>
 * @author Leo Zhang
 * @date 2019年9月23日下午1:19:30
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQApi {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/** 根据access_token获取用户open_id */
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	/**
	 * 获取用户信息的URL，这里虽然没有显性的使用access_token,但是经过super(accessToken,
	 * TokenStrategy.ACCESS_TOKEN_PARAMETER)加载后，请求会默认拼接access_token
	 */
	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

	private String appId;

	private String openId;

	private ObjectMapper objectMapper = new ObjectMapper();

	public QQImpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId = appId;
		String url = String.format(URL_GET_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);

		logger.info(result);

		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}

	@Override
	public QQUserInfo getUserInfo() {
		String url = String.format(URL_GET_USERINFO, appId, openId);
		String result = getRestTemplate().getForObject(url, String.class);

		logger.info(result);

		QQUserInfo userInfo = null;
		try {
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败", e);
		}
	}

	public static String getUrlGetOpenid() {
		return URL_GET_OPENID;
	}

	public static String getUrlGetUserinfo() {
		return URL_GET_USERINFO;
	}

	public String getAppId() {
		return appId;
	}

	public String getOpenId() {
		return openId;
	}

}
