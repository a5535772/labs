package com.leo.labs.es.high.es;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @see https://blog.csdn.net/qq_41911762/article/details/102924263
 */
public class RealHighLevelClientPool implements HighLevelClientPool {
	GenericObjectPool<RestHighLevelClient> objectPool;

	public RealHighLevelClientPool() {
		EShRestHighLevelClientPoolFactory factory = new EShRestHighLevelClientPoolFactory(getHttpHost());
		// 设置对象池的相关参数
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxIdle(20);
		poolConfig.setMaxTotal(100);
		poolConfig.setMinIdle(5);
		poolConfig.setMaxWaitMillis(3000L);
		// 新建一个对象池,传入对象工厂和配置
		objectPool = new GenericObjectPool<>(factory, poolConfig);
	}

	private HttpHost getHttpHost() {
		return new HttpHost("192.168.33.12", 9200, "http");
	}

	@Override
	public RestHighLevelClient get() {
		try {
			return objectPool.borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RestHighLevelClient(RestClient.builder(getHttpHost()));
	}

	@Override
	public void close(RestHighLevelClient client) {
		if (client != null) {
			// 返还对象
			objectPool.returnObject(client);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		objectPool.close();
		super.finalize();
	}
	

}
