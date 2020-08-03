package com.leo.labs.es.high.es;

import org.elasticsearch.client.RestHighLevelClient;

public interface HighLevelClientPool {
	
	RestHighLevelClient get();
	
	void  close(RestHighLevelClient client);
}
