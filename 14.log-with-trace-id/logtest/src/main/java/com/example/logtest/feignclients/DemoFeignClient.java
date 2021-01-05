package com.example.logtest.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "demoFeignClient", url = "http://127.0.0.1:8080")
public interface DemoFeignClient {
	@RequestMapping(value = "/0_1", method = RequestMethod.GET)
	String t0_1();

	@RequestMapping(value = "/0_1_1", method = RequestMethod.GET)
	String t0_1_1();

	@Async
	@RequestMapping(value = "/0_1", method = RequestMethod.GET)
	String t0_1_async();

	@RequestMapping(value = "/0_2", method = RequestMethod.GET)
	String t0_2();

}
