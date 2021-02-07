package com.example.demo.rabbit.producer;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectProducer {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {
		String context = "direct " + System.currentTimeMillis();
		this.rabbitTemplate.convertAndSend("directExchange", "directRoutingkey", context+largeStr);
	}

}