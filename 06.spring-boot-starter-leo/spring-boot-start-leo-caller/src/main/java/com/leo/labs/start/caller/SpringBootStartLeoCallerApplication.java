package com.leo.labs.start.caller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leo.labs.start.jar.demo.uid.api.UidGenerator;



@SpringBootApplication
public class SpringBootStartLeoCallerApplication implements InitializingBean {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStartLeoCallerApplication.class, args);
	}

	@Autowired
	UidGenerator uidGenerator;

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("===============");
		System.out.println(uidGenerator.getUID());
		System.out.println("===============");
	}

}
