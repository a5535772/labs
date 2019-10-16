package com.leo.labs.serviceproxy.server.user.api;

import com.leo.labs.serviceproxy.core.RouteProxyCapable;
import com.leo.labs.serviceproxy.core.RoutingKey;

@RouteProxyCapable
public interface UserApi {
	@RoutingKey(0)
	String findByName(String route, String name);
}
