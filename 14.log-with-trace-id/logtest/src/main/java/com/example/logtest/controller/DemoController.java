package com.example.logtest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.logtest.feignclients.DemoFeignClient;

@RestController
public class DemoController {
	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	Tracer tracer;

	@Autowired
	DemoFeignClient demoFeignClient;

	@GetMapping("/0")
	public String t0() throws InterruptedException {
		logger.info("mehtod:0");
		demoFeignClient.t0_1();
		demoFeignClient.t0_2();
		return "";
	}

	@GetMapping("/0_1")
	public String t0_1() {
		logger.info("mehtod:0_1");
		demoFeignClient.t0_1_1();
		return "";
	}

	@GetMapping("/0_1_1")
	public String t0_1_1() {
		logger.info("mehtod:0_1_1");
		return "";
	}

	@GetMapping("/0_2")
	public String t0_2() {
		logger.info("mehtod:0_2");
		return "";
	}

	@GetMapping("/0_3")
	public String t0_3() {
		logger.info("mehtod:0_3");
		return "";
	}
}
