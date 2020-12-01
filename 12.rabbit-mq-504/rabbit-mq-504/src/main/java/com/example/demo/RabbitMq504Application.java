package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@SpringBootApplication
public class RabbitMq504Application {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMq504Application.class, args);
	}

	@Autowired
	RabbitProperties rabbitProperties;
	@GetMapping("/print")
	public String print() {
		return rabbitProperties.getPassword();
	}
	
	@GetMapping("/set")
	public String set() {
		rabbitProperties.setPassword("admin1");
		return rabbitProperties.getPassword();
	}
}
