package com.leo.labs.es.high.es;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

public class AlibabaHighLevelClientPool implements HighLevelClientPool {
	private static final RequestOptions COMMON_OPTIONS;
	private static final String ES_ADDRES = "http://es-cn-zz11rntq7000ms9hw.public.elasticsearch.aliyuncs.com";
	private static final String ES_USERNAME = "elastic";
	private static final String ES_PASSWORD = "Saic2007";

	static {
		RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
		// 默认缓存限制为100MB，此处修改为30MB。
		builder.setHttpAsyncResponseConsumerFactory(
				new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
		COMMON_OPTIONS = builder.build();
	}

	@Override
	public RestHighLevelClient get() {
		return buidOne();
	}

	private RestHighLevelClient buidOne() {
		// 阿里云Elasticsearch集群需要basic auth验证。
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		// 访问用户名和密码为您创建阿里云Elasticsearch实例时设置的用户名和密码，也是Kibana控制台的登录用户名和密码。
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(ES_USERNAME, ES_PASSWORD));

		// 通过builder创建rest client，配置http client的HttpClientConfigCallback。
		// 单击所创建的Elasticsearch实例ID，在基本信息页面获取公网地址，即为ES集群地址。
		RestClientBuilder builder = RestClient.builder(new HttpHost(ES_ADDRES, 9200))
				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
					@Override
					public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					}
				});

		// RestHighLevelClient实例通过REST low-level client builder进行构造。
		RestHighLevelClient highClient = new RestHighLevelClient(builder);
		return highClient;
	}

	@Override
	public void close(RestHighLevelClient client) {
		if (client != null) {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
