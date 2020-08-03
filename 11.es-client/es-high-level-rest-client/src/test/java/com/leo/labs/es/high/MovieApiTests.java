package com.leo.labs.es.high;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.leo.labs.es.high.api.MovieApi;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieApiTests {

	@Autowired
	MovieApi movieApi;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getById() throws Exception {
		String value = movieApi.getById("1");
		System.out.println(value);
	}

}
