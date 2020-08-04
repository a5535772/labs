package com.leo.labs.es.high;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.leo.labs.es.high.api.StudentApi;
import com.leo.labs.es.high.dto.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentApiTests {

	@Autowired
	StudentApi studentApi;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void addOne() throws Exception {
		// given
		Student student = getZhangSan();
		// when
		IndexResponse resp = studentApi.addOne(student);
		// then
		System.out.println(resp.toString());
	}

	@Test
	public void addByList() throws Exception {
		List<Student> studentList = new ArrayList<>();
		studentList.add(getZhangSan());
		studentList.add(getLiSi());
		studentList.add(getWangWu());
		BulkResponse bulkResponse = studentApi.addByList(studentList);
		System.out.println("==========================");

		for (BulkItemResponse bulkItemResponse : bulkResponse.getItems()) {
			System.out.println(bulkItemResponse.getResponse().toString());
		}
		System.out.println("==========================");
	}

	@Test
	public void search() throws Exception {
		SearchResponse zhangsanResponse = studentApi.search(getZhangSan());
		SearchResponse lisiResponse = studentApi.search(getLiSi());
		System.out.println("==========================");
		System.out.println(zhangsanResponse.toString());
		System.out.println(lisiResponse.toString());
		System.out.println("==========================");

	}

	Student getZhangSan() {
		Student student = new Student("100000000001", "张三", "male");
		return student;
	}

	Student getLiSi() {
		Student student = new Student("100000000002", "lisi", "male");
		return student;
	}

	Student getWangWu() {
		Student student = new Student("100000000003", "wangwu", "female");
		return student;
	}

}
