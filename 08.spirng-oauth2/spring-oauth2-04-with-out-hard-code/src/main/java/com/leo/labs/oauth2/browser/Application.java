package com.leo.labs.oauth2.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = { "com.leo.labs.oauth2.core", "com.leo.labs.oauth2.browser" })
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
