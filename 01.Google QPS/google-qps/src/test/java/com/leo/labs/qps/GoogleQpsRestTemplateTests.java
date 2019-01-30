package com.leo.labs.qps;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.leo.labs.qps.controller.HelloController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoogleQpsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoogleQpsRestTemplateTests {
	/**
	 * @LocalServerPort 提供了 @Value("${local.server.port}") 的代替
	 */
	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		String url = String.format("http://localhost:%d/", port);
		System.out.println(String.format("port is : [%d]", port));
		this.base = new URL(url);
	}

	@Test
	public void getHelloTest() throws Exception {
		ResponseEntity<String> response = this.restTemplate.getForEntity(this.base.toString() + "/hello", String.class,
				"");
		String respString = response.getBody();
		assertThat(respString).isEqualTo(HelloController.Succ);
	}
	
	@Test
	public void getHelloLimitedTest() throws Exception {
//		String respBody1 = getHelloLimited();
//		String respBody2 = getHelloLimited();
//		String respBody3 = getHelloLimited();
//		assertThat(respBody1).isEqualTo(IndexController.Succ);
//		assertThat(respBody2).isEqualTo(IndexController.Succ);
//		assertThat(respBody3).isEqualTo(IndexController.Fail);
		for (int i = 0; i <10; i++) {
			System.out.println(getHelloLimited());
		}
	}

	private String getHelloLimited() {
		String respBody=null;
			ResponseEntity<String> resp = this.restTemplate.getForEntity(this.base.toString() + "/helloLimited", String.class,
					"");
			respBody= resp.getBody();
		return respBody;
	}	
}