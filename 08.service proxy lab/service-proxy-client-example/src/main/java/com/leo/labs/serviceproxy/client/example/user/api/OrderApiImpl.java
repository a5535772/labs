package com.leo.labs.serviceproxy.client.example.user.api;

import com.leo.labs.serviceproxy.server.user.api.OrderApi;
/**
 * 这里只是为了方便测试，写到了客户端，实际上可以用dubbo的client
 * @author LeoZhangli
 *
 */
public class OrderApiImpl implements OrderApi {

	@Override
	public String findByName(String route, String name) {
		return "Order" + route + name;
	}

}
