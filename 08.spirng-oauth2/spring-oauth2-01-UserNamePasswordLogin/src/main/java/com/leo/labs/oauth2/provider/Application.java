package com.leo.labs.oauth2.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 127.0.0.1:8080/hello
	 * <p/>
	 * 默认用户名 user
	 */
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
}
