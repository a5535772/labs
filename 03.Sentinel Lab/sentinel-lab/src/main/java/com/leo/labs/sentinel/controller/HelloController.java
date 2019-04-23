package com.leo.labs.sentinel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leo.labs.sentinel.service.HelloService;

@RestController
public class HelloController {
	@Autowired
	HelloService helloService;

	@GetMapping("helloWorld")
	public String helloWorld() {
		return helloService.helloWorld();
	}
	
	@GetMapping("helloWorldNoBlocker")
	public String helloWorldNoBlocker() {
		return helloService.helloWorldNoBlocker();
	}	

}
