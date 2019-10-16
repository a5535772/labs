package com.leo.labs.serviceproxy.core.loader;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;

import com.leo.labs.serviceproxy.core.agent.AbstractClientAgent;

public abstract class AbstractProxyLoader implements InitializingBean {
	/** 所有需要被代理的接口，通过 <code>loadAllInterfaces</code>方法载入 */
	protected Class<?>[] apis;
	/** 总代理，请使用配置文件进行注入 */
	protected AbstractClientAgent clientAgent;
	/** 运行时缓存，初始化加载 */
	protected Map<String, Object> proxyMap = new ConcurrentHashMap<>();

	public void setClientAgent(AbstractClientAgent clientAgent) {
		this.clientAgent = clientAgent;
	}

	/**
	 * 代理单个接口
	 * 
	 * @param interfaceClass
	 */
	protected void proxySingleInterface(Class<?> interfaceClass) {
		ClassLoader classLoader = interfaceClass.getClassLoader();
		Class<?>[] interfaces = new Class[] { interfaceClass };
		Object proxyedInterface = Proxy.newProxyInstance(classLoader, interfaces, clientAgent);
		proxyMap.put(interfaceClass.getName(), proxyedInterface);

	}

	/**
	 * 扫描所有需要被代理的接口
	 */
	protected abstract void loadAllInterfaces();

	/**
	 * 获取被proxy的bean
	 * @param classFullName
	 * @return
	 */
	public Object getProxyedClientBean(String classFullName) {
		return this.proxyMap.get(classFullName);
	}

	/**
	 * 初始化所有proxy
	 */
	protected void initApis() {
		for (int i = 0; i < apis.length; i++) {
			proxySingleInterface(apis[i]);
		}
	}

	public void afterPropertiesSet() throws Exception {
		this.loadAllInterfaces();
		initApis();
	}
}
