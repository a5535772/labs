package com.example.demo.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.rabbit.producer.DirectProducer;

@Component
@EnableScheduling
public class SchedulingSend {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DirectProducer directProducer;

	@Scheduled(fixedDelay = 500l)
	public void sender() throws Exception {
		for (int i = 0; i < 100; i++) {
			directProducer.send();
		}
		System.out.println("SchedulingSend SUCC");
	}
}
