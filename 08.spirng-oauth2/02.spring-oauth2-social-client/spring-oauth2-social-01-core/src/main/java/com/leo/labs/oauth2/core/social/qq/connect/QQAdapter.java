package com.leo.labs.oauth2.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.leo.labs.oauth2.core.social.qq.api.QQApi;
import com.leo.labs.oauth2.core.social.qq.api.QQUserInfo;

public class QQAdapter implements ApiAdapter<QQApi> {

	@Override
	public boolean test(QQApi api) {
		return true;
	}

	@Override
	public void setConnectionValues(QQApi api, ConnectionValues values) {
		QQUserInfo qqUserInfo = api.getUserInfo();
		values.setDisplayName(qqUserInfo.getNickname());
		values.setImageUrl(qqUserInfo.getFigureurl_qq_1());
		values.setProfileUrl(null);// 个人主页，QQ没有
		values.setProviderUserId(qqUserInfo.getOpenId());
	}

	@Override
	public UserProfile fetchUserProfile(QQApi api) {
		return null;
	}

	/**
	 * 
	 * QQ没有，微博有
	 * <p/>
	 * 如果用户想要更新微博动态，就可以用这个
	 */
	@Override
	public void updateStatus(QQApi api, String message) {
	}

}
