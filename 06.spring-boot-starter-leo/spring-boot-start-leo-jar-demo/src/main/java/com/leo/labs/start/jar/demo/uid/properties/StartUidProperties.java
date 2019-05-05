package com.leo.labs.start.jar.demo.uid.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author LeoZhangli
 *
 */
@ConfigurationProperties("startuid")
public class StartUidProperties {

	private static final String DEFAULT_NAME = "leo";

	private String serviceId = DEFAULT_NAME;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


}
