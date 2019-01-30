package com.leo.labs.hystrix;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HystrixCommandApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HystrixRestTemplateTests {
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
		ResponseEntity<String> response = this.restTemplate.getForEntity(this.base.toString() + "/hello", String.class);
		String respString = response.getBody();
		System.out.println(respString);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	// @Test
	// public void getHello100Test() throws Exception {
	// ExecutorService executor= Executors.newFixedThreadPool(4);
	// for (int i = 0; i < 100; i++) {
	// executor.execute(new HelloTask(base, restTemplate));
	// }
	//
	// Thread.sleep(10000L);
	// executor.shutdown();
	// }

	@Test
	public void getHello100Test() throws Throwable {
		int runnerCount = 100;
		TestRunnable[] tasks = new TestRunnable[runnerCount];
		for (int i = 0; i < runnerCount; i++) {
			tasks[i] = new HelloTask(base, restTemplate);
		}
		new MultiThreadedTestRunner(tasks).runTestRunnables();
	}

	private class HelloTask extends TestRunnable {
		private URL base;
		private TestRestTemplate restTemplate;

		public HelloTask(URL base, TestRestTemplate restTemplate) {
			super();
			this.base = base;
			this.restTemplate = restTemplate;
		}

		@Override
		public void runTest() throws Throwable {
			ResponseEntity<String> response = this.restTemplate.getForEntity(this.base.toString() + "/hello",
					String.class);
			String respString = response.getBody();
			System.out.println(respString);

		}
	}

}