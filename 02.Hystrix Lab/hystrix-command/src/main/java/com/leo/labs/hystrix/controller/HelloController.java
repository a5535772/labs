package com.leo.labs.hystrix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leo.labs.hystrix.filters.command.LeoLabDefaultResponse;
import com.leo.labs.hystrix.filters.command.LeoLabResponse;
import com.leo.labs.hystrix.filters.command.file.LeoLabHystrixommand;

@RestController
public class HelloController {
	static final String apiName = "hello";

	@GetMapping("hello")
	public LeoLabResponse hello() {
		LeoLabResponse leoLabResponse = null;
		try {
			LeoLabHystrixommand cmd = new LeoLabHystrixommand(apiName);
			leoLabResponse = cmd.execute();
		} catch (Exception e) {
			leoLabResponse=new LeoLabDefaultResponse(-1);
		}
		return leoLabResponse;
	}

}
