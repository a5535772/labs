package com.leo.labs.es.high.es;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class SimpleHighLevelClientPool implements HighLevelClientPool {

	@Override
	public RestHighLevelClient get() {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("192.168.33.12", 9200, "http")));
		return client;
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
