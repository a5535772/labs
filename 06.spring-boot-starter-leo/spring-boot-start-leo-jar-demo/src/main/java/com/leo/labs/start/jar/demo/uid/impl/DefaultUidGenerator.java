
package com.leo.labs.start.jar.demo.uid.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.leo.labs.start.jar.demo.uid.api.UidGenerator;
import com.leo.labs.start.jar.demo.uid.exception.UidGenerateException;

public class DefaultUidGenerator implements UidGenerator, InitializingBean {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String serviceId;

	
	
	public DefaultUidGenerator(String serviceId) {
		super();
		this.serviceId = serviceId;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isEmpty(serviceId)) {
			logger.warn("【分布式组件服务】-初始化参数不足，不予初始化");
			return;
		}

	}

	@Override
	public synchronized String getUID() throws UidGenerateException {
		// JUST A DEMO
		return serviceId + System.currentTimeMillis();
	}

	@Override
	public String parseUID(long uid) {
		return "";
	}

}
