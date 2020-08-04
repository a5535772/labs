package com.leo.labs.es.high.es;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 可以研究以下
 */
public class EShRestHighLevelClientPoolFactory implements PooledObjectFactory<RestHighLevelClient> {
	private HttpHost[] httpHosts;

	public EShRestHighLevelClientPoolFactory(HttpHost... httpHosts) {// 构造方法传入连接信息
		this.httpHosts = httpHosts;
	}

	public PooledObject<RestHighLevelClient> makeObject() throws Exception {
		return new DefaultPooledObject<RestHighLevelClient>(new RestHighLevelClient(RestClient.builder(httpHosts)));
	}

	public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
		pooledObject.getObject().close();
	}

	public boolean validateObject(PooledObject<RestHighLevelClient> pooledObject) {
		return true;
	}

	public void activateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {

	}

	public void passivateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {

	}
}