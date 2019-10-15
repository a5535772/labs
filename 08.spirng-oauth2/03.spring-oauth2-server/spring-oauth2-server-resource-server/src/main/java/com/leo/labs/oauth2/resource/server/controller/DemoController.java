package com.leo.labs.oauth2.resource.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/lab")
public class DemoController {

	@GetMapping("/baidu/get/user")
	public String baidu() {
		return "baidu";
	}

	@GetMapping("/bing/get/user")
	public String bing() {
		return "bing";
	}
}
