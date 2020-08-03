package com.leo.labs.es.high.api;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leo.labs.es.high.es.HighLevelClientPool;

@Service
public class MovieImpl implements MovieApi {
	@Autowired
	@Qualifier("simpleHighLevelClientPool")
	HighLevelClientPool clientPool;

	@Override
	public String getById(String id) {
		RestHighLevelClient client = clientPool.get();
		GetResponse resp = null;
		try {
			resp = client.get(new GetRequest("movies", "_doc", id));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			clientPool.close(client);
		}
		return resp == null ? "" : resp.toString();
	}

	@Override
	public String getByTitle(String title) {
		return null;
	}

}
