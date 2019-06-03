package com.leo.labs.serviceproxy.core.loader;

import org.springframework.core.annotation.Order;

import com.leo.labs.serviceproxy.core.PorxyConstant;

@Order(PorxyConstant.ORDER_OF_PROXY_LOADER)
public class SimpleProxyLoader extends AbstractProxyLoader {

	public Class<?>[] getApis() {
		return apis;
	}

	public void setApis(Class<?>[] apis) {
		this.apis = apis;
	}

	/**
	 * 代理单个接口
	 * 
	 * @param interfaceClass
	 */
	@Override
	protected void loadAllInterfaces() {
		// 通过配置文件注入，这里什么都不用做了，后面可以加一个PackageScannerProxyLoader
	}

}
