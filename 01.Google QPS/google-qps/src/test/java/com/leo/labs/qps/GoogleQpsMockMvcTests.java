package com.leo.labs.qps;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.leo.labs.qps.controller.HelloController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleQpsMockMvcTests {
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}

	@Test
	public void getHello() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/hello");
		MvcResult result = mvc.perform(builder.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		System.out.println(result.getResponse());
	}
	
	@Test
	public void gethelloLimited() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/helloLimited");
		for (int i = 0; i < 10; i++) {
			MvcResult result = mvc.perform(builder.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print()).andReturn();
			System.out.println(result.getResponse());
		}
		
		
	}

}
