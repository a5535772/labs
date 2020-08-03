package com.leo.labs.es.high.api;

import java.util.List;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;

import com.leo.labs.es.high.dto.Student;

public interface StudentApi {

	IndexResponse addOne(Student student);
	
	BulkResponse addByList(List<Student> studentList);

	IndexResponse getById(String id);
	
	SearchResponse search(Student student);

}
