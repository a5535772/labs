package com.example.demo.rabbit.consumer;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues = "direct")
public class DirectConsumer {

	@RabbitHandler
	public void process(String hello, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
		System.out.println("Receiver  : " + deliveryTag + " | " + hello);
		try {
//			channel.basicAck(deliveryTag, false);
			this.doMyJob(hello);
			Thread.sleep(10000l);
			System.out.println("处理数据succ，..." + deliveryTag + " | " + hello);
		} catch (Exception e) {
			e.getStackTrace();
			try {
				System.out.println("处理数据fail，正在退还数据..." + " " + hello);
//				channel.basicNack(deliveryTag, false, true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void doMyJob(String msg) throws Exception {
		System.out.println("正在处理数据...." + msg);
//		throw new RuntimeException();
	}

}
