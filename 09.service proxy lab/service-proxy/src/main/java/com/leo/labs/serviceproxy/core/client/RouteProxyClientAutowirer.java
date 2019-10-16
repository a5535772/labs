package com.leo.labs.serviceproxy.core.client;

import java.lang.reflect.Field;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;

import com.leo.labs.serviceproxy.core.PorxyConstant;
import com.leo.labs.serviceproxy.core.RouteProxyCapable;
import com.leo.labs.serviceproxy.core.RouteProxyClient;
import com.leo.labs.serviceproxy.core.loader.AbstractProxyLoader;

@Order(PorxyConstant.ORDER_OF_PROXY_AUTOWRRER)
public class RouteProxyClientAutowirer implements ApplicationContextAware, InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(RouteProxyClientAutowirer.class);

	private ApplicationContext applicationContext;

	@Autowired
	AbstractProxyLoader proxyLoader;

	@Override
	public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	void init() throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RouteProxyClient.class);
		for (Object bean : beans.values()) {

			try {
				initRouteProxyClient(bean);
				logger.info("Loading bean " + bean.getClass());
			} catch (IllegalArgumentException e1) {
				logger.error(e1.getMessage());
				throw e1;
			} catch (IllegalAccessException e2) {
				logger.error(e2.getMessage());
				throw e2;
			} catch (Exception e3) {
				logger.error(e3.getMessage());
				throw e3;
			}
		}
	}

	void initRouteProxyClient(Object bean) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = bean.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			RouteProxyClient routeProxyClient = field.getAnnotation(RouteProxyClient.class);
			RouteProxyCapable routeProxyCapable = field.getType().getAnnotation(RouteProxyCapable.class);
			if (routeProxyClient != null && routeProxyCapable != null) {
				field.setAccessible(true);
				Object value = proxyLoader.getProxyedClientBean(field.getType().getName());
				field.set(bean, value);
			}
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
}
