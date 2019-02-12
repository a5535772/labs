package com.leo.labs.qps;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

		Thread.sleep(2000l);
	}

	/**
	 * 由于springboot启动需要加载时间，在这短时间内 RateLimiter 早已初始化完成，并就行了N秒的等待，所以这里初始化之后，一开始可以通过两个
	 * 
	 * @throws Exception
	 */
	@Test
	public void will_get_2_succ_when_not_sleep() throws Exception {
		List<String> list = new ArrayList<>();

		list.add(getHelloLimited());
		list.add(getHelloLimited());
		list.add(getHelloLimited());
		list.add(getHelloLimited());

		assertThat(list.get(0)).isEqualTo(HelloController.Succ);
		assertThat(list.get(1)).isEqualTo(HelloController.Succ);
		assertThat(list.get(2)).isEqualTo(HelloController.Fail);
		assertThat(list.get(3)).isEqualTo(HelloController.Fail);

		Thread.sleep(2000l);
	}

	@Test
	public void will_get_3_succ_when_sleep_1_second_after_2_succ() throws Exception {
		List<String> list = new ArrayList<>();

		list.add(getHelloLimited());
		list.add(getHelloLimited());
		Thread.sleep(1000l);
		list.add(getHelloLimited());
		list.add(getHelloLimited());

		assertThat(list.get(0)).isEqualTo(HelloController.Succ);
		assertThat(list.get(1)).isEqualTo(HelloController.Succ);
		assertThat(list.get(2)).isEqualTo(HelloController.Succ);
		assertThat(list.get(3)).isEqualTo(HelloController.Fail);

		Thread.sleep(2000l);
	}

	@Test
	public void will_get_4_succ_when_sleep_2_second_after_2_succ() throws Exception {
		List<String> list = new ArrayList<>();

		list.add(getHelloLimited());
		list.add(getHelloLimited());
		Thread.sleep(2000l);
		list.add(getHelloLimited());
		list.add(getHelloLimited());

		assertThat(list.get(0)).isEqualTo(HelloController.Succ);
		assertThat(list.get(1)).isEqualTo(HelloController.Succ);
		assertThat(list.get(2)).isEqualTo(HelloController.Succ);
		assertThat(list.get(3)).isEqualTo(HelloController.Succ);

		Thread.sleep(2000l);
	}

	private String getHelloLimited() {
		String respBody = null;
		ResponseEntity<String> resp = this.restTemplate.getForEntity(this.base.toString() + "/helloLimited",
				String.class, "");
		respBody = resp.getBody();
		return respBody;
	}
}