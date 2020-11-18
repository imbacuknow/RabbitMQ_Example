package com.imbac.myrabbitmq.services;

import com.imbac.myrabbitmq.entity.PersonalCard;

import org.json.simple.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

     @Autowired
     private AmqpTemplate amqpTemplate;

     // set exchange and routingKey
     @Value("${rabbitmq.exchange}")
     String exchange;
     @Value("${rabbitmq.routingkey}")
     private String routingKey;

     public void sendMessageDataToQueue(PersonalCard personalCard) {
          JSONObject jo = new JSONObject();
          jo.put("first_name", personalCard.getFirst_name());
          jo.put("last_name", personalCard.getLast_name());
          jo.put("personal_id", personalCard.getPersonal_id());
          amqpTemplate.convertAndSend(exchange, routingKey, jo);
          System.out.println("Send message: \n " + jo + "\n");
     }
}
