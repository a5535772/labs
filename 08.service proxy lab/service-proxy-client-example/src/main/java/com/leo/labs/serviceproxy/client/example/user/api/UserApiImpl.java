package com.leo.labs.serviceproxy.client.example.user.api;

import com.leo.labs.serviceproxy.server.user.api.UserApi;

/**
 * 这里只是为了方便测试，写到了客户端，实际上可以用dubbo的client
 * 
 * @author LeoZhangli
 *
 */
public class UserApiImpl implements UserApi {

	@Override
	public String findByName(String route, String name) {
		return route + name;
	}

}
