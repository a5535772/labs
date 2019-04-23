package com.leo.labs.sentinel;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.leo.labs.sentinel.configuration.SentinelAspectConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SentinelLabApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SentinelRestTemplateTests {
	/**
	 * @LocalServerPort 提供了 @Value("${local.server.port}") 的代替
	 */
	@LocalServerPort
	private int port;

	private URL base;

	static int ten = 10;

	static final String status = "status";

	static final String body = "body";

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		String url = String.format("http://localhost:%d/", port);
		System.out.println(String.format("port is : [%d]", port));
		this.base = new URL(url);
	}

	@Test
	public void 访问10次helloworld_由于有blocker_所以每次都会返回200的状态_然而仍然会被限流() throws Exception {
		String url = "helloWorld";
		List<Map<String, String>> list = new ArrayList<>(ten);
		for (int i = 0; i < ten; i++) {
			list.add(doGetByUrl(url));
		}
		for (int i = 0; i < ten; i++) {
			Map<String, String> map = list.get(i);
			assertThat(map.get(status)).isEqualTo("200");
			if (i < SentinelAspectConfiguration.qps) {
				assertThat(map.get(body)).isEqualTo("helloWorld");
			}
			System.out.println(map);
		}
	}

	@Test
	public void 访问10次helloWorldNoBlocker_第一次httpstatus是200_后续是500() throws Exception {
		String url = "helloWorldNoBlocker";
		List<Map<String, String>> list = new ArrayList<>(ten);
		for (int i = 0; i < ten; i++) {
			list.add(doGetByUrl(url));
		}
		for (int i = 0; i < ten; i++) {
			Map<String, String> map = list.get(i);
			if (i < SentinelAspectConfiguration.qps) {
				assertThat(map.get(status)).isEqualTo("200");
			} else {
				assertThat(map.get(status)).isEqualTo("500");
			}
			System.out.println(map);
		}
	}

	public Map<String, String> doGetByUrl(String url) throws Exception {
		ResponseEntity<String> resp = this.restTemplate.getForEntity(this.base.toString() + "/" + url, String.class,
				"");
		Map<String, String> result = new HashMap<>();
		result.put(status, resp.getStatusCode().toString());
		result.put(body, resp.getBody());
		return result;

	}
}