package com.javainuse.service;

import org.json.simple.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.javainuse.model.Employee;

@Service
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${javainuse.rabbitmq.exchange}") // กำหนดค่าให้ตัวแปร
	private String exchange;

	@Value("${javainuse.rabbitmq.routingkey}")
	private String routingkey;
	String kafkaTopic = "java_in_use_topic";

	public void send(Employee company) {
		JSONObject jo = new JSONObject();
		jo.put("key", company.getEmpId());
		amqpTemplate.convertAndSend(exchange, routingkey, jo);
		System.out.println("Send msg = " + jo);
	}
}