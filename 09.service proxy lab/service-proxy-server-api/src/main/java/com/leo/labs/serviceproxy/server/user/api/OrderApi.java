package com.leo.labs.serviceproxy.server.user.api;

import com.leo.labs.serviceproxy.core.RouteProxyCapable;
import com.leo.labs.serviceproxy.core.RoutingKey;

@RouteProxyCapable
@RoutingKey(0)
public interface OrderApi {
	String findByName(String route, String name);
}
