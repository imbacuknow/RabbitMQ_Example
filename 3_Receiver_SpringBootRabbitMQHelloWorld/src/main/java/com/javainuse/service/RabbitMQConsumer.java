package com.javainuse.service;

import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.javainuse.model.Employee;

@Component
public class RabbitMQConsumer {

     @RabbitListener(queues = "${javainuse.rabbitmq.queue}")
     public void recievedMessage(Employee employee) {
          JSONObject jo = new JSONObject();
          jo.put("empId", employee.getEmpId());
          jo.put("empName", employee.getEmpName());
          System.out.println("Recieved Message From RabbitMQ: " + jo);
     }
}