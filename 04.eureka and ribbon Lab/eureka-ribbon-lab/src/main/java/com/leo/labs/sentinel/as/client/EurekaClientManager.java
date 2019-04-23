package com.leo.labs.sentinel.as.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

/**
 * 
 * @author LeoZhangli
 *
 */
public class EurekaClientManager {
	static String hostOfEureka = "http://zhangli:dev@localhost:8761/eureka";
	private static ApplicationInfoManager applicationInfoManager;
	private static EurekaClient eurekaClient;
	private static EurekaClientConfigBean bean;

	private static synchronized ApplicationInfoManager initializeApplicationInfoManager(
			EurekaInstanceConfig instanceConfig) {
		if (applicationInfoManager == null) {
			EurekaInstanceConfigBean instanceConfigBean = new EurekaInstanceConfigBean(
					new InetUtils(new InetUtilsProperties()));
			instanceConfigBean.setPreferIpAddress(true);
			applicationInfoManager = new ApplicationInfoManager(instanceConfigBean);
		}

		return applicationInfoManager;
	}

	private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager,
			EurekaClientConfigBean bean) {
		if (eurekaClient == null) {
			eurekaClient = new DiscoveryClient(applicationInfoManager, bean);
		}

		return eurekaClient;
	}

	private EurekaClientManager() {

	}

	public static EurekaClient getInstance() {
		if (eurekaClient == null) {
			synchronized (EurekaClientManager.class) {
				if (eurekaClient == null) {
					ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(
							new MyDataCenterInstanceConfig());
					bean = new EurekaClientConfigBean();
					Map<String, String> map = new HashMap<String, String>();
					// PropertiesParse.getProperty("base-eureka.node1")

					map.put(EurekaClientConfigBean.DEFAULT_ZONE, hostOfEureka);
					bean.setServiceUrl(map);
					eurekaClient = initializeEurekaClient(applicationInfoManager, bean);
				}
			}
		}
		return eurekaClient;
	}

	public void shutdown() {
		if (eurekaClient != null) {
			eurekaClient.shutdown();
		}
	}

	public static void main(String[] args) {
		// create the client

		List<InstanceInfo> instancesByVipAddress = getInstance().getInstancesByVipAddress("CONSUMER-SOM-FEIGN", false);
		System.out.println(instancesByVipAddress.size());

		StringBuilder sb = new StringBuilder();

		for (InstanceInfo instance : instancesByVipAddress) {
			InstanceInfo.InstanceStatus status = instance.getStatus();
			sb.append("http://").append(instance.getHostName()).append(":").append(instance.getPort());
			System.out.println(instance.getHostName());
			System.out.println(instance.getPort());
			System.out.println(status.name());
		}

	}

}