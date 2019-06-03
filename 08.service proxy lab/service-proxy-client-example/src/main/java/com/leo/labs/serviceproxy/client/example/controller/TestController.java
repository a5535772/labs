package com.leo.labs.serviceproxy.client.example.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leo.labs.serviceproxy.core.RouteProxyClient;
import com.leo.labs.serviceproxy.server.user.api.OrderApi;
import com.leo.labs.serviceproxy.server.user.api.UserApi;

@RestController
@RouteProxyClient
public class TestController implements InitializingBean {
	@RouteProxyClient
	UserApi userApi;
	@RouteProxyClient
	OrderApi orderApi;

	@GetMapping("/1")
	public String testProxy1(String route) {
		return userApi.findByName(route, "zhangli");
	}

	@GetMapping("/2")
	public String testProxy2(String route) {
		return orderApi.findByName(route, "zhangli");
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		// Field[] fields = this.getClass().getDeclaredFields();
		// for (int i = 0; i < fields.length; i++) {
		// Field field = fields[i];
		// RouteProxyClient routeProxyClient =
		// field.getAnnotation(RouteProxyClient.class);
		// RouteProxyCapable routeProxyCapable =
		// field.getType().getAnnotation(RouteProxyCapable.class);
		//
		// if (routeProxyClient != null && routeProxyCapable != null) {
		// Object value = proxyLoader.getProxyedClientBean(field.getType().getName());
		// field.set(this, value);
		// }
		// }
	}
}
