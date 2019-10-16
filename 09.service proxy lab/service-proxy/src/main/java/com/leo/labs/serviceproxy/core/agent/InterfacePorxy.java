package com.leo.labs.serviceproxy.core.agent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leo.labs.serviceproxy.core.InterfaceProxyMark;
import com.leo.labs.serviceproxy.error.RoutingKeyNotProxyedException;

/**
 * 实际处理业务的类
 * 
 * @author LeoZhangli
 *
 */
@InterfaceProxyMark
public class InterfacePorxy {
	private final Logger logger = LoggerFactory.getLogger(InterfacePorxy.class);

	private static final ThreadLocal<String> routeHolder = new ThreadLocal<>();

	private Map<String, Object> apiRouteMap = new HashMap<>();

	private String proxyClzStr;

	public String getProxyClzStr() {
		return proxyClzStr;
	}

	public void setProxyClzStr(String proxyClzStr) {
		this.proxyClzStr = proxyClzStr;
	}

	public Map<String, Object> getApiRouteMap() {
		return apiRouteMap;
	}

	public void setApiRouteMap(Map<String, Object> apiRouteMap) {
		this.apiRouteMap = apiRouteMap;
	}

	/**
	 * 
	 * @param route
	 */
	public void markRoute(Object route) {
		routeHolder.set(route.toString());
	}

	/**
	 * 
	 * @param method
	 * @param args
	 * @return
	 * @throws RoutingKeyNotProxyedException
	 * @throws Exception
	 */
	public Object call(Method method, Object[] args) throws RoutingKeyNotProxyedException, Exception {
		Object proxy = this.apiRouteMap.get(routeHolder.get());

		if (proxy == null) {
			throw new RoutingKeyNotProxyedException("Routing key try to touch zone " + routeHolder.get()
					+ ",one zone must be proxyed  ===> class: " + proxyClzStr + " method:" + method.getName() + " args:"
					+ args + " routingKey.value():" + routeHolder.get());
		}
		logger.debug("RouteKey is " + routeHolder.get() + " Method:" + method + " will invoke (proxy,args)   proxy:"
				+ proxy + " args:" + args);
		return method.invoke(proxy, args);

	}

}
