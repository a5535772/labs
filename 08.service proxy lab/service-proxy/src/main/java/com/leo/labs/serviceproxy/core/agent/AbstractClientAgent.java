package com.leo.labs.serviceproxy.core.agent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;

import com.leo.labs.serviceproxy.core.InterfaceProxyMark;
import com.leo.labs.serviceproxy.core.PorxyConstant;
import com.leo.labs.serviceproxy.core.RoutingKey;
import com.leo.labs.serviceproxy.error.ProxyException;
import com.leo.labs.serviceproxy.error.ProxyNotExistsException;
import com.leo.labs.serviceproxy.error.RoutingKeyNotExistException;

/**
 * 
 * @Description: 客户端API的代理类
 * @author LeoZhangli
 * @date 2016-06-03 下午1:09:14
 * @version V1.0
 */

@Order(PorxyConstant.ORDER_OF_CLIENT_AGENT)
public abstract class AbstractClientAgent implements InvocationHandler, ApplicationContextAware, InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(AbstractClientAgent.class);

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	protected Map<String, InterfacePorxy> porxyMap = new ConcurrentHashMap<>();

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			/* 不代理接口的toString()方法，采用object.toString()直接返回 */
			if (method.toString().equals("public java.lang.String java.lang.Object.toString()"))
				return this.toString();
			Class<?> targetClass = method.getDeclaringClass();
			String targetClassName = targetClass.getName();

			InterfacePorxy interfaceProxy = porxyMap.get(targetClassName);
			if (interfaceProxy == null) {
				throw new ProxyNotExistsException(targetClassName + " is not proxyed by Agent");
			}

			RoutingKey routingKey = method.getAnnotation(RoutingKey.class) != null
					? method.getAnnotation(RoutingKey.class)
					: targetClass.getAnnotation(RoutingKey.class);
			if (routingKey == null) {
				throw new RoutingKeyNotExistException("Routintkey not exist ===> class: " + targetClassName + " method:"
						+ method.getName() + " args:" + args + " routingKey.value():" + routingKey.value());
			}

			try {
				interfaceProxy.markRoute(args[routingKey.value()]);
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException(
						"Routintkey out of index ===> class: " + targetClassName + " method:" + method.getName()
								+ " args:" + args + " routingKey.value():" + routingKey.value());
			}
			return interfaceProxy.call(method, args);
		} catch (ProxyNotExistsException e1) {
			logger.error(e1.getMessage());
			throw e1;
		} catch (RoutingKeyNotExistException e2) {
			logger.error(e2.getMessage());
			throw e2;
		} catch (ProxyException e98) {
			logger.error(e98.getMessage());
			throw e98;
		} catch (IndexOutOfBoundsException e99) {
			logger.error(e99.getMessage());
			throw e99;
		} catch (Exception e100) {
			logger.error(e100.getMessage());
			throw e100;
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(InterfaceProxyMark.class);
		for (Object bean : beans.values()) {
			InterfacePorxy proxy = (InterfacePorxy) bean;
			if (porxyMap.get(proxy.getProxyClzStr()) == null) {
				porxyMap.put(proxy.getProxyClzStr(), (InterfacePorxy) bean);
			}
		}
	}

}
