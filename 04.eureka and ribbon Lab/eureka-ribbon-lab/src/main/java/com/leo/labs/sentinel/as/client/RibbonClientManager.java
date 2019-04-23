package com.leo.labs.sentinel.as.client;

import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.DummyPing;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAffinityServerListFilter;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.netflix.niws.loadbalancer.EurekaNotificationServerListUpdater;
/**
 * 
 * @author LeoZhangli
 *
 */
public class RibbonClientManager {

	private final static Logger LOG = LoggerFactory.getLogger(RibbonClientManager.class);

	private static EurekaClient eurekaClient = EurekaClientManager.getInstance();

	private static RibbonClientManager instance;

	private static Provider<EurekaClient> eurekaClientProvider;

	static {
		eurekaClientProvider = new Provider<EurekaClient>() {
			public EurekaClient get() {
				return eurekaClient;
			}
		};

	}

	private RibbonClientManager() {
	}

	public static RibbonClientManager getManager() {
		if (instance == null) {
			synchronized (RibbonClientManager.class) {
				if (instance == null) {
					instance = new RibbonClientManager();
				}
			}
		}
		return instance;
	}

	private static ConcurrentHashMap<String, DynamicServerListLoadBalancer<DiscoveryEnabledServer>> lbServerMap = new ConcurrentHashMap<>();

	public void initByAppName(String appName) {
		DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues();

		DynamicServerListLoadBalancer<DiscoveryEnabledServer> lb = new DynamicServerListLoadBalancer<DiscoveryEnabledServer>(
				config, new AvailabilityFilteringRule(), new DummyPing(),
				new DiscoveryEnabledNIWSServerList(appName, eurekaClientProvider),
				new ZoneAffinityServerListFilter<DiscoveryEnabledServer>(),
				new EurekaNotificationServerListUpdater(eurekaClientProvider));
		lbServerMap.put(appName, lb);
	}

	public String getUrl(String vipAddress) {
		DynamicServerListLoadBalancer<DiscoveryEnabledServer> lb = lbServerMap.get(vipAddress);

		if (lb == null) {
			LOG.error("the LoadBalanced server {} no available server url!", vipAddress);
			return null;
		}

		Server chooseServer = lb.chooseServer();
		if (chooseServer == null) {
			LOG.error("the service {} no available server url!", vipAddress);
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("http://").append(chooseServer.getHostPort());
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		RibbonClientManager manager = RibbonClientManager.getManager();
		manager.initByAppName("CONSUMER-SOM-FEIGN");
		
		
		while(true) {
			Thread.sleep(1000l);
			System.out.println("=========================================================");
			System.out.println(manager.getUrl("CONSUMER-SOM-FEIGN"));
		}

	}

}