package com.example.demo.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {

	@Bean
	public Queue directedMessage() {
		return new Queue("direct");
	}

	@Bean
	DirectExchange directExchange() {
		return new DirectExchange("directExchange");
	}

	@Bean
	Binding bindingHeadersExchange(Queue directedMessage, DirectExchange directExchange) {
		return BindingBuilder.bind(directedMessage).to(directExchange).with("directRoutingkey");
	}

}
