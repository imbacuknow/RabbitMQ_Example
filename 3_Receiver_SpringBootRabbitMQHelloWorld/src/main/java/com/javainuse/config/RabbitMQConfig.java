package com.javainuse.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	// กำหนด config สำหรับ sender

	@Value("${javainuse.rabbitmq.queue}") // เซ็ตค่า queue ให้ ตัวแปร
	String queueName;

	@Value("${javainuse.rabbitmq.exchange}") // เซ็ตค่า exchange ให้ ตัวแปร
	String exchange;

	@Value("${javainuse.rabbitmq.routingkey}") // เซ็ตค่า routingkey ให้ ตัวแปร
	private String routingkey;

	@Bean
	Queue queue() {
		// System.out.println("1");
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange exchange() {
		// System.out.println("2");
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		// System.out.println("3");
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		// System.out.println("4");
		return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		// System.out.println("5");
		return rabbitTemplate;
	}
}