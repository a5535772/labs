package com.leo.labs.es.high.api;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.leo.labs.es.high.dto.Student;
import com.leo.labs.es.high.es.HighLevelClientPool;

@Service
public class StudentImpl implements StudentApi {
	@Autowired
	@Qualifier("simpleHighLevelClientPool")
	HighLevelClientPool clientPool;

	private static final String STUDENT_INDEX = "students";

	private static final String DOC_TYPE = "_doc";

	/**
	 * @see https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.2/java-rest-high-document-index.html#_providing_the_document_source
	 */
	@Override
	public IndexResponse addOne(Student student) {
		// given
		IndexRequest indexRequest = new IndexRequest(STUDENT_INDEX, DOC_TYPE, student.getId());
		indexRequest.source(new Gson().toJson(student), XContentType.JSON);

		// send
		RestHighLevelClient client = clientPool.get();
		IndexResponse indexResponse = null;
		try {
			indexResponse = client.index(indexRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			clientPool.close(client);
		}

		return indexResponse;
	}

	/**
	 * @see https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.2/java-rest-high-document-bulk.html
	 */
	@Override
	public BulkResponse addByList(List<Student> studentList) {
		// given
		BulkRequest bulkRequest = new BulkRequest();
		for (Student student : studentList) {
			IndexRequest indexRequest = new IndexRequest(STUDENT_INDEX, DOC_TYPE, student.getId());
			indexRequest.source(new Gson().toJson(student), XContentType.JSON);
			bulkRequest.add(indexRequest);
		}

		// send
		RestHighLevelClient client = clientPool.get();
		BulkResponse bulkResponse = null;
		try {
			bulkResponse = client.bulk(bulkRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			clientPool.close(client);
		}
		return bulkResponse;
	}

	/**
	 * @see https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.2/java-rest-high-document-get.html
	 */
	@Override
	public IndexResponse getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.2/java-rest-high-search.html
	 */
	@Override
	public SearchResponse search(Student student) {
		SearchRequest searchRequest = new SearchRequest(STUDENT_INDEX);
		searchRequest.types(DOC_TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.query(QueryBuilders.termQuery("name", student.getName()));
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(5);
		searchRequest.source(searchSourceBuilder);

		// send
		RestHighLevelClient client = clientPool.get();
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			clientPool.close(client);
		}
		return searchResponse;
	}

}
