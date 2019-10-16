package com.leo.labs.serviceproxy.client.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:spring.xml"})
public class ServiceProxyClientExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProxyClientExampleApplication.class, args);
	}

}
